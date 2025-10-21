package com.example.awaq_agromo.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.awaq_agromo.data.local.DataStoreManager
import com.example.awaq_agromo.data.remote.dto.LoginUserRequest
import com.example.awaq_agromo.data.remote.dto.LoginUserResponse
import com.example.awaq_agromo.domain.usecase.user.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val data: LoginUserResponse) : LoginState()
    data class Error(val message: String) : LoginState()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state: StateFlow<LoginState> = _state

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _state.value = LoginState.Loading
            try {
                val request = LoginUserRequest(user_email = email, password = password)
                val response = loginUseCase("agromo", request)

                if (response.isSuccessful && response.body() != null) {
                    val loginData = response.body()!!

                    // Save token persistently
                    dataStoreManager.saveToken(loginData.token)
                    Log.d("LoginViewModel", "Token saved: ${loginData.token}")

                    _state.value = LoginState.Success(loginData)
                } else {
                    _state.value = LoginState.Error(
                        response.errorBody()?.string() ?: "Error desconocido"
                    )
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Login failed", e)
                _state.value = LoginState.Error(e.localizedMessage ?: "Error desconocido")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            dataStoreManager.clearToken() // remove token on logout
            _state.value = LoginState.Idle
        }
    }
}