package com.salman.chaigpt.data.model.chat

import com.salman.chaigpt.domain.model.chat.Message

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/22/2023.
 * Used to send a POST request to the chat endpoint.
 * @see [ChatApiService]
 */
data class ChatMessageRequest(
    val model: String,
    val messages: List<Message>,
    val temperature: Double,
)
