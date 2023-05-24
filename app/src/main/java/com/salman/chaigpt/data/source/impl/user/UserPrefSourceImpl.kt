package com.salman.chaigpt.data.source.impl.user

import androidx.datastore.core.DataStore
import com.salman.chaigpt.Settings
import com.salman.chaigpt.data.source.api.SettingsSource
import kotlinx.coroutines.flow.first

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/24/2023.
 */
class UserPrefSourceImpl(
    private val dataStore: DataStore<Settings>
): SettingsSource {

    override suspend fun getSessionToken(): String? {
        return dataStore.data.first().sessionToken
    }

    override suspend fun setSessionToken(token: String) {
        dataStore.updateData {
            it.toBuilder()
                .setSessionToken(token)
                .build()
        }
    }

    override suspend fun getRememberMe(): Boolean {
        return dataStore.data.first().rememberMe
    }

    override suspend fun setRememberMe(rememberMe: Boolean) {
        dataStore.updateData {
            it.toBuilder()
                .setRememberMe(rememberMe)
                .build()
        }
    }

    override suspend fun canAutoLogin(): Boolean {
        return getSessionToken() != null && getRememberMe()
    }
}