package com.salman.chaigpt.data.source.api

import com.salman.chaigpt.domain.model.chat.Message
import kotlinx.coroutines.flow.Flow

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/22/2023.
 */
interface ChatDataSource {
    val messagesFlow: Flow<List<Message>>

    suspend fun startNewChat()
    suspend fun sendMessage(message: String)
}