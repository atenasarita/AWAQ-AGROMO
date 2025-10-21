package com.example.awaq_agromo.data.remote.dto

data class RegisterUserRequest(
    val username: String,
    val password: String,
    val user_email: String
)