package com.example.vidasalud.presentation.registro

import com.example.vidasalud.data.repository.RegistroDiario
import java.time.LocalDate

data class RegistroUiState(
    val registroActual: RegistroDiario = RegistroDiario(),
    val fechaSeleccionada: LocalDate = LocalDate.now(),
    val mensajeExito: String? = null,
    val mensajeError: String? = null,
    val isLoading: Boolean = false
)