package com.salman.chaigpt.data.model

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/24/2023.
 */
data class User(
    val name: String,
    val email: String,
    val sessionToken: String? = null,
)
