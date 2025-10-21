package com.example.awaq_agromo.domain.usecase.user

import com.example.awaq_agromo.data.remote.dto.LoginUserRequest
import com.example.awaq_agromo.data.remote.dto.LoginUserResponse
import com.example.awaq_agromo.domain.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(tenant: String, request: LoginUserRequest): Response<LoginUserResponse> {
        return repository.loginUser(tenant, request)
    }
}