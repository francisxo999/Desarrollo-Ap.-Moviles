package com.example.vidasalud.presentation.principal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vidasalud.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ResumenUiState(
    val nombreUsuario: String = "..."
)

class ResumenViewModel : ViewModel() {

    private val authRepository = AuthRepository()

    private val _uiState = MutableStateFlow(ResumenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        cargarNombreUsuario()
    }

    private fun cargarNombreUsuario() {
        viewModelScope.launch {
            val nombre = authRepository.obtenerNombreUsuario()
            if (nombre != null) {
                _uiState.update { it.copy(nombreUsuario = nombre) }
            } else {
                _uiState.update { it.copy(nombreUsuario = "Usuario") }
            }
        }
    }
}