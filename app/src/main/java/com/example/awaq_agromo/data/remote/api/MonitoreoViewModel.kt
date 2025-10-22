package com.example.awaq_agromo.data.remote.api

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.awaq_agromo.data.local.store.WeatherSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonitoreoViewModel @Inject constructor(
    // OJO: WeatherRepository está en el MISMO package (data.remote.api)
    private val repo: WeatherRepository
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _lastSnapshot = MutableStateFlow<WeatherSnapshot?>(null)
    val lastSnapshot: StateFlow<WeatherSnapshot?> = _lastSnapshot

    fun fetchAndSave(
        context: Context,
        label: String,
        lat: Double,
        lon: Double,
        apiKey: String
    ) {
        _error.value = null
        _loading.value = true
        viewModelScope.launch {
            try {
                val snap = repo.fetchAndSaveSnapshot(context, label, lat, lon, apiKey)
                _lastSnapshot.value = snap
            } catch (t: Throwable) {
                _error.value = t.message ?: "Error desconocido"
            } finally {
                _loading.value = false
            }
        }
    }
}