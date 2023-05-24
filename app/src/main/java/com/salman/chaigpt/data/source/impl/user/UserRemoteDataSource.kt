package com.salman.chaigpt.data.source.impl.user

import com.parse.ParseException
import com.parse.ParseUser
import com.salman.chaigpt.data.model.User
import com.salman.chaigpt.data.source.api.UserDataStore
import com.salman.chaigpt.domain.model.exception.AuthenticationFailedException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/24/2023.
 */
class UserRemoteDataSource : UserDataStore {
    private var _currentUser: User? = null
    override val currentUser: User?
        get() = _currentUser

    private val _currentUserFlow = MutableSharedFlow<User?>()
    override val currentUserFlow: Flow<User?>
        get() = _currentUserFlow

    override suspend fun signUp(username: String, email: String, password: String) =
        withContext(Dispatchers.IO) {
            val user = ParseUser()
            user.username = username
            user.email = email
            user.setPassword(password)
            try {
                user.signUp()
                updateUser()
            } catch (e: ParseException) {
                throw AuthenticationFailedException("Could not sign up user")
            }
        }

    override suspend fun signIn(username: String, password: String) {
        try {
            ParseUser.logIn(username, password)
            updateUser()
        } catch (e: ParseException) {
            throw AuthenticationFailedException("Could not sign in user")
        }
    }

    override suspend fun signIn(token: String) {
        try {
            ParseUser.become(token)
            updateUser()
        } catch (e: ParseException) {
            throw AuthenticationFailedException("Could not sign in user")
        }
    }

    override suspend fun signOut() {
        ParseUser.logOut()
        updateUser()
    }

    private suspend fun updateUser() {
        val currentUser = ParseUser.getCurrentUser() ?: return
        _currentUser = User(
            name = currentUser.username!!,
            email = currentUser.email!!,
            sessionToken = currentUser.sessionToken
        )
        _currentUserFlow.emit(_currentUser)
    }
}