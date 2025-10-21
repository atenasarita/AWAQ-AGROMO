package com.example.awaq_agromo.data.remote.dto

data class UserResponse(
    val authenticated: Boolean,
    val user: UserDto,
    val tenant: String
)

data class UserDto(
    val id: Int,
    val username: String,
    val user_email: String,
    val tenant: String,
    val lastAccess: String?,
    val lastLogin: String?,
)