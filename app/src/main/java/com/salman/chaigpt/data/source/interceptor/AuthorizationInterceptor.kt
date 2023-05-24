package com.salman.chaigpt.data.source.interceptor

import com.salman.chaigpt.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/22/2023.
 */
class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
        val key = BuildConfig.OPEN_AI_KEY
        val authKey = "Bearer $key"
        newRequest.addHeader("Authorization", authKey)

        return chain.proceed(newRequest.build())
    }
}