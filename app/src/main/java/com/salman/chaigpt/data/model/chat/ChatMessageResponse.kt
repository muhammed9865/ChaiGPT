package com.salman.chaigpt.data.model.chat

data class ChatMessageResponse(
    val choices: List<Choice>,
    val created: Int,
    val id: String,
    val model: String,
    val `object`: String,
    val usage: Usage
)