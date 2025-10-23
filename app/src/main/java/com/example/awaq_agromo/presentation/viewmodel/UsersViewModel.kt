package com.example.awaq_agromo.presentation.viewmodel

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
import com.example.awaq_agromo.data.local.repository.FormRepository

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getCurrentUseCase: GetCurrentUseCase,
    private val dataStoreManager: DataStoreManager,
    val cultivosStore: CultivosUsuario,
    private val formRepository: FormRepository // inject your repository
) : ViewModel() {

    private val _user = MutableStateFlow<UserDto?>(null)
    val user: StateFlow<UserDto?> = _user

    private val _crops = MutableStateFlow<Set<String>>(emptySet())
    val crops: StateFlow<Set<String>> = _crops

    private val _error = MutableStateFlow<String?>(null)

    // --- Form state ---
    private val _currentFormId = MutableStateFlow<Int?>(null)
    val currentFormId: StateFlow<Int?> = _currentFormId

    private val _selectedCrops = MutableStateFlow<Set<String>>(emptySet())
    val selectedCrops: StateFlow<Set<String>> = _selectedCrops

    private val _sowingDate = MutableStateFlow<String?>(null)
    val sowingDate: StateFlow<String?> = _sowingDate

    init {
        fetchCurrentUser()
    }

    fun fetchCurrentUser() {
        viewModelScope.launch {
            dataStoreManager.token.collect { token ->
                if (token != null) {
                    val response: Response<UserResponse> = getCurrentUseCase("agromo", "Bearer $token")
                    if (response.isSuccessful && response.body() != null) {
                        val currentUser = response.body()!!.user
                        _user.value = currentUser

                        currentUser.id.let { userId ->
                            cultivosStore.readSelected(userId.toString())
                                .collect { cropSet ->
                                    _crops.value = cropSet
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

    // --- Crops helpers ---
    fun addCrop(crop: String) {
        viewModelScope.launch {
            user.value?.id?.let { id ->
                cultivosStore.addOne(id.toString(), crop)
                _selectedCrops.value = _selectedCrops.value + crop
            }
        }
    }

    fun removeCrop(crop: String) {
        viewModelScope.launch {
            user.value?.id?.let { id ->
                cultivosStore.removeOne(id.toString(), crop)
                _selectedCrops.value = _selectedCrops.value - crop
            }
        }
    }

    // --- Form functions ---
    fun savePartialForm(
        selectedCrops: Set<String>? = null,
        sowingDate: String? = null,
        humedadDesc: String? = null,
        humedadValue: Int? = null
    ) {
        viewModelScope.launch {
            val cropsString = selectedCrops?.joinToString(",")
            formRepository.upsertPartialForm(
                id = _currentFormId.value,
                userId = user.value?.id,
                selectedCrops = cropsString ?: _selectedCrops.value.joinToString(","),
                sowingDate = sowingDate ?: _sowingDate.value,
                humedadDesc = humedadDesc,
                humedadValue = humedadValue
            ).also {
                // update local state
                if (selectedCrops != null) _selectedCrops.value = selectedCrops
                if (sowingDate != null) _sowingDate.value = sowingDate
            }
        }
    }

    fun loadFormById(formId: Int) {
        viewModelScope.launch {
            val form = formRepository.getFormById(formId)
            _currentFormId.value = form?.id
            form?.let {
                _selectedCrops.value = it.selectedCrops?.split(",")?.toSet() ?: emptySet()
                _sowingDate.value = it.sowingDate
            }
        }
    }
}
