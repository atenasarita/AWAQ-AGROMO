package com.example.awaq_agromo.domain.repository

import com.example.awaq_agromo.data.remote.dto.*
import retrofit2.Response

interface UserRepository {
    suspend fun registerUser(tenant: String, request: RegisterUserRequest): Response<RegisterUserResponse>
    suspend fun loginUser(tenant: String, request: LoginUserRequest): Response<LoginUserResponse>
    suspend fun getCurrentUser(tenant: String, token: String): Response<UserResponse>
}