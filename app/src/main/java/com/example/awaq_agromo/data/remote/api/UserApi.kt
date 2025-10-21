package com.example.awaq_agromo.data.remote.api

import com.example.awaq_agromo.data.remote.dto.RegisterUserRequest
import com.example.awaq_agromo.data.remote.dto.RegisterUserResponse
import com.example.awaq_agromo.data.remote.dto.LoginUserRequest
import com.example.awaq_agromo.data.remote.dto.LoginUserResponse
import com.example.awaq_agromo.data.remote.dto.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {

    @POST("/api/{tenant}/users/register")
    suspend fun registerUser(
        @Path("tenant") tenant: String,
        @Body request: RegisterUserRequest
    ): Response<RegisterUserResponse>

    @POST("/api/{tenant}/users/login")
    suspend fun loginUser(
        @Path("tenant") tenant: String,
        @Body request: LoginUserRequest
    ): Response<LoginUserResponse>

    @GET("api/{tenant}/users/me")
    suspend fun getCurrentUser(
        @Path("tenant") tenant: String,
        @Header("Authorization") token: String // optional if your backend requires a token
    ): Response<UserResponse>
}