package com.example.awaq_agromo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.awaq_agromo.data.local.DataStoreManager
import com.example.awaq_agromo.data.remote.dto.UserDto
import com.example.awaq_agromo.data.remote.dto.UserResponse
import com.example.awaq_agromo.domain.usecase.user.GetCurrentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getCurrentUseCase: GetCurrentUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _user = MutableStateFlow<UserDto?>(null)
    val user: StateFlow<UserDto?> = _user

    private val _error = MutableStateFlow<String?>(null)

    fun fetchCurrentUser() {
        viewModelScope.launch {
            // Read token from DataStore
            dataStoreManager.token.collect { token ->
                if (token != null) {
                    val response: Response<UserResponse> = getCurrentUseCase("agromo", "Bearer $token")
                    if (response.isSuccessful && response.body() != null) {
                        _user.value = response.body()!!.user
                    } else {
                        _error.value = "Error: ${response.errorBody()?.string() ?: "Unknown error"}"
                    }
                } else {
                    _error.value = "No token found, please login"
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            dataStoreManager.clearToken() // clear the saved token
            _user.value = null // clear current user
        }
    }


}