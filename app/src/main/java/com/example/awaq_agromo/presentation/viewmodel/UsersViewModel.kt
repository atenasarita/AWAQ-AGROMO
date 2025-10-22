package com.example.awaq_agromo.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.awaq_agromo.data.local.DataStoreManager
import com.example.awaq_agromo.data.local.store.CultivosUsuario
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
    private val dataStoreManager: DataStoreManager,
    val cultivosStore: CultivosUsuario,

    ) : ViewModel() {

    private val _user = MutableStateFlow<UserDto?>(null)
    val user: StateFlow<UserDto?> = _user


    private val _crops = MutableStateFlow<Set<String>>(emptySet())
    val crops: StateFlow<Set<String>> = _crops

    private val _error = MutableStateFlow<String?>(null)

    fun fetchCurrentUser() {
        viewModelScope.launch {
            dataStoreManager.token.collect { token ->
                if (token != null) {
                    val response: Response<UserResponse> = getCurrentUseCase("agromo", "Bearer $token")
                    if (response.isSuccessful && response.body() != null) {
                        val currentUser = response.body()!!.user
                        _user.value = currentUser

                        // Start collecting crops for this user
                        currentUser.id.let { userId ->
                            cultivosStore.readSelected(userId.toString())
                                .collect { cropSet ->
                                    _crops.value = cropSet
                                    Log.d("UserViewModel", "Crops updated: $cropSet")
                                }
                        }

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

    fun addCrop(crop: String) {
        viewModelScope.launch {
            user.value?.id?.let { id ->
                cultivosStore.addOne(id.toString(), crop)
            }
        }
    }

    fun loadCropsForUser(userId: Int?) {
        viewModelScope.launch {
            cultivosStore.readSelected(userId.toString()).collect { set ->
                _crops.value = set
            }
        }
    }


    fun removeCrop(crop: String) {
        viewModelScope.launch {
            user.value?.id?.let { id ->
                cultivosStore.removeOne(id.toString(), crop)
            }
        }
    }

}