package com.salman.chaigpt.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.salman.chaigpt.Settings
import com.salman.chaigpt.data.source.api.ChatDataSource
import com.salman.chaigpt.data.source.impl.chat.ChatApiService
import com.salman.chaigpt.data.source.impl.chat.ChatRemoteDataSource
import com.salman.chaigpt.data.source.interceptor.AuthorizationInterceptor
import com.salman.chaigpt.data.source.preference.internal.SettingsSerializer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/22/2023.
 */

val dataModule = module {
    // Retrofit client Single
    single {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor())
            .addInterceptor(loggingInterceptor)
            .build()
    }

    // Retrofit Single
    single {
        val client = get<OkHttpClient>()
        val baseUrl = "https://api.openai.com/v1/"
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    // ChatApiService Single
    single {
        val retrofit = get<Retrofit>()
        retrofit.create(ChatApiService::class.java)
    }

    // ChatRemoteDataSource Single
    single<ChatDataSource> {
        ChatRemoteDataSource(
            service = get()
        )
    }

    single { get<Context>().dataStoreSettings }
}

private val Context.dataStoreSettings: DataStore<Settings> by dataStore(
    fileName = "settings.pb",
    serializer = SettingsSerializer
)