package com.example.awaq_agromo.domain.usecase.user

import com.example.awaq_agromo.data.remote.dto.UserResponse
import com.example.awaq_agromo.domain.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject

class GetCurrentUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(tenant: String, token: String): Response<UserResponse> {
        return repository.getCurrentUser(tenant, token)
    }
}