package com.example.awaq_agromo.domain.usecase.user

import com.example.awaq_agromo.data.remote.dto.RegisterUserRequest
import com.example.awaq_agromo.data.remote.dto.RegisterUserResponse
import com.example.awaq_agromo.domain.repository.UserRepository
import jakarta.inject.Inject
import retrofit2.Response

class RegisterUseCase @Inject constructor(
    private val repository: UserRepository
){
    suspend operator fun invoke (tenant: String, request: RegisterUserRequest): Response<RegisterUserResponse> {
        return repository.registerUser(tenant, request)
    }
}