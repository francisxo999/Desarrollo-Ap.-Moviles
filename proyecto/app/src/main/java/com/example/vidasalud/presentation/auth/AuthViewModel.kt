package com.example.vidasalud.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vidasalud.ui.navigation.RutasApp
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AuthUiState(
    val email: String = "",
    val contrasena: String = "",
    val errorEmail: String? = null,
    val errorContrasena: String? = null,
    val nombre: String = "",
    val confirmarContrasena: String = "",
    val errorNombre: String? = null,
    val errorConfirmarContrasena: String? = null
)

class AuthViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<String>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, errorEmail = null) }
    }

    fun onContrasenaChange(contrasena: String) {
        _uiState.update {
            it.copy(
                contrasena = contrasena,
                errorContrasena = null,
                errorConfirmarContrasena = null
            )
        }
    }

    fun onNombreChange(nombre: String) {
        _uiState.update { it.copy(nombre = nombre, errorNombre = null) }
    }

    fun onConfirmarContrasenaChange(contrasena: String) {
        _uiState.update {
            it.copy(
                confirmarContrasena = contrasena,
                errorConfirmarContrasena = null
            )
        }
    }

    fun onLoginClicked() {
        val email = _uiState.value.email
        val contrasena = _uiState.value.contrasena
        var hayErrores = false

        _uiState.update { it.copy(errorNombre = null, errorConfirmarContrasena = null) }

        if (email.isBlank()) {
            _uiState.update { it.copy(errorEmail = "Campo obligatorio") }
            hayErrores = true
        }

        if (contrasena.isBlank()) {
            _uiState.update { it.copy(errorContrasena = "Campo obligatorio") }
            hayErrores = true
        }

        if (!hayErrores) {
            viewModelScope.launch {
                _navigationEvent.emit(RutasApp.PantallaPrincipal.ruta)
            }
        }
    }

    fun onRegistroClicked() {
        val state = _uiState.value
        var hayErrores = false

        if (state.nombre.isBlank()) {
            _uiState.update { it.copy(errorNombre = "Campo obligatorio") }
            hayErrores = true
        }
        if (state.email.isBlank()) {
            _uiState.update { it.copy(errorEmail = "Campo obligatorio") }
            hayErrores = true
        }
        if (state.contrasena.isBlank()) {
            _uiState.update { it.copy(errorContrasena = "Campo obligatorio") }
            hayErrores = true
        }
        if (state.confirmarContrasena.isBlank()) {
            _uiState.update { it.copy(errorConfirmarContrasena = "Campo obligatorio") }
            hayErrores = true
        } else if (state.contrasena != state.confirmarContrasena) {
            _uiState.update { it.copy(errorConfirmarContrasena = "Las contraseñas no coinciden") }
            hayErrores = true
        }

        if (!hayErrores) {
            viewModelScope.launch {
                _navigationEvent.emit(RutasApp.PantallaPrincipal.ruta)
            }
        }
    }
}