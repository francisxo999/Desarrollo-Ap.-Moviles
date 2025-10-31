package com.example.vidasalud.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vidasalud.data.repository.AuthRepository
import com.example.vidasalud.data.repository.ResultadoAuth
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
    val errorConfirmarContrasena: String? = null,
    val isLoading: Boolean = false,
    val errorGeneral: String? = null
)

class AuthViewModel : ViewModel() {

    private val authRepository = AuthRepository()

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<String>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, errorEmail = null, errorGeneral = null) }
    }
    fun onContrasenaChange(contrasena: String) {
        _uiState.update {
            it.copy(
                contrasena = contrasena,
                errorContrasena = null,
                errorConfirmarContrasena = null,
                errorGeneral = null
            )
        }
    }
    fun onNombreChange(nombre: String) {
        _uiState.update { it.copy(nombre = nombre, errorNombre = null, errorGeneral = null) }
    }
    fun onConfirmarContrasenaChange(contrasena: String) {
        _uiState.update {
            it.copy(
                confirmarContrasena = contrasena,
                errorConfirmarContrasena = null,
                errorGeneral = null
            )
        }
    }

    fun onLoginClicked() {
        if (!validarCamposLogin()) return

        _uiState.update { it.copy(isLoading = true, errorGeneral = null) }

        viewModelScope.launch {
            val resultado = authRepository.iniciarSesion(
                email = _uiState.value.email,
                contrasena = _uiState.value.contrasena
            )
            when (resultado) {
                is ResultadoAuth.Exito -> {
                    _navigationEvent.emit(RutasApp.PantallaPrincipal.ruta)
                }
                is ResultadoAuth.Error -> {
                    _uiState.update { it.copy(isLoading = false, errorGeneral = resultado.mensaje) }
                }
            }
        }
    }

    fun onRegistroClicked() {
        if (!validarCamposRegistro()) return

        _uiState.update { it.copy(isLoading = true, errorGeneral = null) }

        viewModelScope.launch {
            val resultado = authRepository.crearUsuario(
                nombre = _uiState.value.nombre,
                email = _uiState.value.email,
                contrasena = _uiState.value.contrasena
            )
            when (resultado) {
                is ResultadoAuth.Exito -> {
                    _navigationEvent.emit(RutasApp.PantallaPrincipal.ruta)
                }
                is ResultadoAuth.Error -> {
                    _uiState.update { it.copy(isLoading = false, errorGeneral = resultado.mensaje) }
                }
            }
        }
    }

    private fun esEmailValido(email: String): Boolean {
        return Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$").matches(email)
    }

    private fun validarCamposLogin(): Boolean {
        val email = _uiState.value.email
        val contrasena = _uiState.value.contrasena
        var hayErrores = false

        if (email.isBlank()) {
            _uiState.update { it.copy(errorEmail = "Campo obligatorio") }
            hayErrores = true
        } else if (!esEmailValido(email)) {
            _uiState.update { it.copy(errorEmail = "Formato de email no válido") }
            hayErrores = true
        }

        if (contrasena.isBlank()) {
            _uiState.update { it.copy(errorContrasena = "Campo obligatorio") }
            hayErrores = true
        } else if (contrasena.length < 6) {
            _uiState.update { it.copy(errorContrasena = "Mínimo 6 caracteres") }
            hayErrores = true
        }

        return !hayErrores
    }

    private fun validarCamposRegistro(): Boolean {
        val state = _uiState.value
        var hayErrores = false

        if (state.nombre.isBlank()) {
            _uiState.update { it.copy(errorNombre = "Campo obligatorio") }
            hayErrores = true
        }

        if (state.email.isBlank()) {
            _uiState.update { it.copy(errorEmail = "Campo obligatorio") }
            hayErrores = true
        } else if (!esEmailValido(state.email)) {
            _uiState.update { it.copy(errorEmail = "Formato de email no válido") }
            hayErrores = true
        }

        if (state.contrasena.isBlank()) {
            _uiState.update { it.copy(errorContrasena = "Campo obligatorio") }
            hayErrores = true
        } else if (state.contrasena.length < 6) {
            _uiState.update { it.copy(errorContrasena = "Mínimo 6 caracteres") }
            hayErrores = true
        }

        if (state.confirmarContrasena.isBlank()) {
            _uiState.update { it.copy(errorConfirmarContrasena = "Campo obligatorio") }
            hayErrores = true
        } else if (state.contrasena.isNotBlank() && state.contrasena != state.confirmarContrasena) {
            _uiState.update { it.copy(errorConfirmarContrasena = "Las contraseñas no coinciden") }
            hayErrores = true
        }

        return !hayErrores
    }
}