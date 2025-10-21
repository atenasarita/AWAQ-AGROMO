package com.example.awaq_agromo.data.repository

import com.example.awaq_agromo.data.remote.api.UserApi
import com.example.awaq_agromo.data.remote.dto.*
import com.example.awaq_agromo.domain.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: UserApi
) : UserRepository {

    override suspend fun registerUser(tenant: String, request: RegisterUserRequest): Response<RegisterUserResponse> {
        return api.registerUser(tenant, request)
    }

    override suspend fun loginUser(tenant: String, request: LoginUserRequest): Response<LoginUserResponse> {
        return api.loginUser(tenant, request)
    }

    override suspend fun getCurrentUser(tenant: String, token: String): Response<UserResponse> {
        return api.getCurrentUser(tenant, token)
    }
}