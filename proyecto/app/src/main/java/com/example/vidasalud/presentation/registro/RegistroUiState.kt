package com.example.vidasalud.presentation.registro

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
data class RegistroUiState(
    val fechaSeleccionada: LocalDate = LocalDate.now(),
    val registroId: String? = null, // ID del documento de Firestore (si existe)

    // Campos del formulario como String para validación
    val peso: String = "",
    val calorias: String = "",
    val sueno: String = "",
    val pasos: String = "",

    // Campos de error para TextFields
    val errorPeso: String? = null,
    val errorCalorias: String? = null,
    val errorSueno: String? = null,
    val errorPasos: String? = null,

    // Estado general
    val isLoading: Boolean = false,
    val mensajeError: String? = null,
    val mensajeExito: String? = null
)