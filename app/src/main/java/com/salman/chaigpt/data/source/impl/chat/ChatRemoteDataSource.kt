package com.salman.chaigpt.data.source.impl.chat

import com.salman.chaigpt.data.model.chat.ChatMessageRequest
import com.salman.chaigpt.data.model.chat.ChatMessageResponse
import com.salman.chaigpt.data.source.api.ChatDataSource
import com.salman.chaigpt.domain.model.chat.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/22/2023.
 */
class ChatRemoteDataSource(
    private val service: ChatApiService,
) : ChatDataSource {

    companion object {
        // Constants used for the request.
        private const val MODEL = "gpt-3.5-turbo"
        private const val TEMPERATURE = 0.7
        private const val USER_ROLE = "user"
    }

    private val _messages = mutableListOf<Message>()

    private val _messagesFlow = MutableSharedFlow<List<Message>>()
    override val messagesFlow: Flow<List<Message>>
        get() {
            return _messagesFlow
        }

    override suspend fun startNewChat() {
        _messages.clear()
        _messagesFlow.emit(emptyList())
    }

    override suspend fun sendMessage(message: String) {
        val request = createRequest(message)
        emitMessages()

        val response = service.sendMessage(request)

        val responseMessage = extractMessage(response)
        responseMessage?.let {
            _messages.add(it)
            emitMessages()
        }
    }

    private fun createRequest(message: String): ChatMessageRequest {
        val internalMessage = Message(message, USER_ROLE)
        _messages.add(internalMessage)

        return ChatMessageRequest(
            model = MODEL,
            messages = _messages,
            temperature = TEMPERATURE,
        )
    }

    private fun extractMessage(response: ChatMessageResponse): Message? {
        return response.choices.firstOrNull()?.message
    }

    private suspend fun emitMessages() {
        _messagesFlow.emit(_messages.toList())
    }

}