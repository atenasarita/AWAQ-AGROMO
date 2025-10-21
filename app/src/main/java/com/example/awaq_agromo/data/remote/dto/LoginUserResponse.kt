package com.example.awaq_agromo.data.remote.dto

data class LoginUserResponse(
    val message: String,
    val token: String,
    val token_type: String,
    val expires_in: String,
    val user: UserDto
)