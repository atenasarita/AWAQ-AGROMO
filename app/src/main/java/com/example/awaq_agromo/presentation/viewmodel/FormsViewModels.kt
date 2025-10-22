package com.example.awaq_agromo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.awaq_agromo.data.local.entity.VariedadEntity
import com.example.awaq_agromo.data.local.repository.HumedadRepository
import com.example.awaq_agromo.data.local.repository.VariedadRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormsViewModels @Inject constructor(
    private val repository: VariedadRepository
) : ViewModel() {

    fun saveVariedad(userId: Int, crops: List<String>, sowingDate: String?) {
        viewModelScope.launch {
            val form = VariedadEntity(
                userId = userId,
                crops = crops.joinToString(","), // store as CSV
                sowingDate = sowingDate
            )
            repository.insertForm(form)
        }
    }

    fun getVariedadesForUser(userId: Int): StateFlow<List<VariedadEntity>> {
        return repository.getForms(userId)
            .stateIn(viewModelScope, SharingStarted.Companion.Lazily, emptyList())
    }
}

@HiltViewModel
class HumedadViewModel @Inject constructor(
    private val humedadRepository: HumedadRepository
) : ViewModel() {

    val lastHumedad = humedadRepository.getLastHumedad().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )

    fun saveHumedad(desc: String, value: Int) {
        viewModelScope.launch {
            humedadRepository.saveHumedad(desc, value)
        }
    }
}
