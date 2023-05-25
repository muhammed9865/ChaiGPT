package com.salman.chaigpt.data.repository

import com.salman.chaigpt.data.model.User
import com.salman.chaigpt.data.source.api.SettingsDataSource
import com.salman.chaigpt.data.source.api.UserDataSource
import com.salman.chaigpt.domain.model.exception.AuthenticationFailedException
import com.salman.chaigpt.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/24/2023.
 */
class UserRepositoryImpl(
    private val userDataSource: UserDataSource,
    private val settingsSource: SettingsDataSource
) : UserRepository {

    override val currentUser: User?
        get() = userDataSource.currentUser

    override val currentUserFlow: Flow<User?>
        get() = userDataSource.currentUserFlow

    override suspend fun signUp(username: String, email: String, password: String): Result<Unit> {
        return trySafely {
            userDataSource.signUp(username, email, password)
        }
    }

    override suspend fun signIn(
        email: String,
        password: String,
        rememberMe: Boolean,
    ): Result<Unit> {
        return trySafely {
            userDataSource.signIn(email, password)
            if (rememberMe) {
                settingsSource.setSessionToken(currentUser?.sessionToken ?: "")
            }
        }
    }

    override suspend fun signInAutomatically(): Result<Unit> {
        val canSignInAuto = settingsSource.canAutoLogin()
        if (canSignInAuto.not()) {
            return Result.failure(AuthenticationFailedException("Token is invalid"))
        }
        val token = settingsSource.getSessionToken()!!
        return trySafely {
            userDataSource.signIn(token)
        }
    }

    override suspend fun signOut(): Result<Unit> {
        return trySafely {
            userDataSource.signOut()
        }
    }

    private suspend fun <T> trySafely(block: suspend () -> T): Result<T> {
        return try {
            Result.success(block())
        } catch (e: AuthenticationFailedException) {
            Result.failure(e)
        }
    }
}