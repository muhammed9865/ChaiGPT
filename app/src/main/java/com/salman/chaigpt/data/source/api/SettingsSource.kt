package com.salman.chaigpt.data.source.api

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/24/2023.
 * Contains user preferences related data source operations
 */
interface SettingsSource {

    suspend fun getSessionToken(): String?
    suspend fun setSessionToken(token: String)

    suspend fun getRememberMe(): Boolean
    suspend fun setRememberMe(rememberMe: Boolean)

    suspend fun canAutoLogin(): Boolean
}