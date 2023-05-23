package com.salman.chaigpt.data.model.chat

import com.salman.chaigpt.domain.model.chat.Message

data class Choice(
    val finish_reason: String,
    val index: Int,
    val message: Message
)