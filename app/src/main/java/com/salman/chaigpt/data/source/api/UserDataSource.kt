package com.salman.chaigpt.data.source.api

import com.salman.chaigpt.data.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/24/2023.
 */
interface UserDataSource {

    val currentUser: User?
    val currentUserFlow: Flow<User?>

    suspend fun signUp(username: String, email: String, password: String)
    suspend fun signIn(username: String, password: String)
    suspend fun signIn(token: String)
    suspend fun signOut()
}