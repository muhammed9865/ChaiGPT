package com.salman.chaigpt.common

import com.salman.chaigpt.domain.model.chat.Message

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/22/2023.
 */
inline fun Message.onUserMessage(block: (String) -> Unit): Message {
    if (role == "user") {
        block(content)
    }
    return this
}

inline fun Message.onAssistantMessage(block: (String) -> Unit): Message {
    if (role == "assistant") {
        block(content)
    }
    return this
}