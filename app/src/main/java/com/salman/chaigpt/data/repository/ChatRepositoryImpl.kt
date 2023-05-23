package com.salman.chaigpt.data.repository

import com.salman.chaigpt.data.remote.api.ChatDataSource
import com.salman.chaigpt.domain.model.chat.Message
import com.salman.chaigpt.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/22/2023.
 */
class ChatRepositoryImpl(
    private val dataSource: ChatDataSource
) : ChatRepository {
    override val messages: Flow<List<Message>>
        get() = dataSource.messagesFlow
            .onEach {
                println("From Repository: $it")
            }

    override suspend fun startNewChat() {
        dataSource.startNewChat()
    }

    override suspend fun sendMessage(message: String) {
        dataSource.sendMessage(message)
    }
}