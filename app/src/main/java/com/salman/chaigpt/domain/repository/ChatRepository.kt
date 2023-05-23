package com.salman.chaigpt.domain.repository

import com.salman.chaigpt.domain.model.chat.Message
import kotlinx.coroutines.flow.Flow

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/22/2023.
 */
interface ChatRepository {

    val messages: Flow<List<Message>>

    /**
     * Starts a new chat by deleting all previous messages
     */
    suspend fun startNewChat()

    /**
     * Sends a message to the chat
     * @param message the message to send
     */
    suspend fun sendMessage(message: String)

}