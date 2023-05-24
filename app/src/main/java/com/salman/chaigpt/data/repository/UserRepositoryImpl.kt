package com.salman.chaigpt.data.repository

import com.salman.chaigpt.data.model.User
import com.salman.chaigpt.data.source.api.SettingsSource
import com.salman.chaigpt.data.source.api.UserDataStore
import com.salman.chaigpt.domain.model.exception.AuthenticationFailedException
import com.salman.chaigpt.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/24/2023.
 */
class UserRepositoryImpl(
    private val userDataStore: UserDataStore,
    private val settingsSource: SettingsSource
) : UserRepository {

    override val currentUser: User?
        get() = userDataStore.currentUser

    override val currentUserFlow: Flow<User?>
        get() = userDataStore.currentUserFlow

    override suspend fun signUp(username: String, email: String, password: String): Result<Unit> {
        return trySafely {
            userDataStore.signUp(username, email, password)
        }
    }

    override suspend fun signIn(
        email: String,
        password: String,
        rememberMe: Boolean,
    ): Result<Unit> {
        return trySafely {
            userDataStore.signIn(email, password)
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
            userDataStore.signIn(token)
        }
    }

    override suspend fun signOut(): Result<Unit> {
        return trySafely {
            userDataStore.signOut()
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