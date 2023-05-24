package com.salman.chaigpt.domain.repository

import com.salman.chaigpt.data.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/24/2023.
 */
interface UserRepository {

    val currentUser: User?
    val currentUserFlow: Flow<User?>

    suspend fun signUp(username: String, email: String, password: String): Result<Unit>
    suspend fun signIn(email: String, password: String, rememberMe: Boolean): Result<Unit>
    suspend fun signInAutomatically(): Result<Unit>
    suspend fun signOut(): Result<Unit>
}