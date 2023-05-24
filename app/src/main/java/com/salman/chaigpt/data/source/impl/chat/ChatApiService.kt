package com.salman.chaigpt.data.source.impl.chat

import com.salman.chaigpt.data.model.chat.ChatMessageRequest
import com.salman.chaigpt.data.model.chat.ChatMessageResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/22/2023.
 */
interface ChatApiService {

    @POST("chat/completions")
    suspend fun sendMessage(
        @Body body: ChatMessageRequest
    ): ChatMessageResponse
}