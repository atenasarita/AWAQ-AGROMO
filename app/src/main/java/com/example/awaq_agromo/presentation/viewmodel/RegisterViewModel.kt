package com.example.awaq_agromo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.awaq_agromo.data.remote.dto.RegisterUserRequest
import com.example.awaq_agromo.data.remote.dto.RegisterUserResponse
import com.example.awaq_agromo.domain.usecase.user.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

sealed class RegistrationState {
    object Idle : RegistrationState()
    object Loading : RegistrationState()
    object Success : RegistrationState()
    data class Error(val message: String) : RegistrationState()
}

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<RegistrationState>(RegistrationState.Idle)
    val state: StateFlow<RegistrationState> = _state

    fun registerUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            _state.value = RegistrationState.Loading
            try {
                val request = RegisterUserRequest(
                    username = name,
                    user_email = email,
                    password = password
                )
                val response: Response<RegisterUserResponse> =
                    registerUseCase("agromo", request)

                if (response.isSuccessful) {
                    _state.value = RegistrationState.Success
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Error desconocido"
                    _state.value = RegistrationState.Error(errorMessage)
                }
            } catch (e: Exception) {
                _state.value = RegistrationState.Error(e.localizedMessage ?: "Error desconocido")
            }
        }
    }
}