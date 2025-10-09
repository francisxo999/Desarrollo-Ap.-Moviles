package com.example.guia_formularios.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.guia_formularios.model.UsuarioErrores
import com.example.guia_formularios.model.UsuarioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UsuarioViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UsuarioUiState())

    val uiState: StateFlow<UsuarioUiState> = _uiState.asStateFlow()

    private val _errores = MutableStateFlow(UsuarioErrores())

    val errores: StateFlow<UsuarioErrores> = _errores.asStateFlow()

    fun updateNombre(nombre: String) {
        _uiState.update { currentState -> currentState.copy(nombre = nombre) }
        if (nombre.isNotBlank()) {
            _errores.update { currentErrores -> currentErrores.copy(nombre = null) }
        }
    }

    fun updateCorreo(correo: String) {
        _uiState.update { currentState -> currentState.copy(correo = correo) }
        if (Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            _errores.update { currentErrores -> currentErrores.copy(correo = null) }
        }
    }

    fun updateClave(clave: String) {
        _uiState.update { currentState -> currentState.copy(clave = clave) }
        if (clave.length >= 6) {
            _errores.update { currentErrores -> currentErrores.copy(clave = null) }
        }
    }

    fun updateDireccion(direccion: String) {
        _uiState.update { currentState -> currentState.copy(direccion = direccion) }
        if (direccion.isNotBlank()) {
            _errores.update { currentErrores -> currentErrores.copy(direccion = null) }
        }
    }

    fun toggleAceptaTerminos() {
        _uiState.update { currentState -> currentState.copy(aceptaTerminos = !currentState.aceptaTerminos) }
    }

    fun validarFormulario(): Boolean {
        val state = _uiState.value

        val erroresActualizados = UsuarioErrores(
            nombre = if (state.nombre.isBlank()) "El nombre es obligatorio" else null,
            correo = if (!Patterns.EMAIL_ADDRESS.matcher(state.correo).matches()) "Correo inválido" else null,
            clave = if (state.clave.length < 6) "Mínimo 6 caracteres" else null,
            direccion = if (state.direccion.isBlank()) "La dirección es obligatoria" else null
        )

        _errores.value = erroresActualizados

        val hayErrores = listOf(
            erroresActualizados.nombre,
            erroresActualizados.correo,
            erroresActualizados.clave,
            erroresActualizados.direccion
        ).any { it != null }

        return !hayErrores && state.aceptaTerminos
    }
}