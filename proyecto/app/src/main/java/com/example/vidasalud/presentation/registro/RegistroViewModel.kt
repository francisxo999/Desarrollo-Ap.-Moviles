package com.example.vidasalud.presentation.registro

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vidasalud.data.repository.RegistroDiario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class RegistroViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val registrosCollection = db.collection("registros_diarios")
    private val userId = auth.currentUser?.uid

    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState: StateFlow<RegistroUiState> = _uiState

    init {
        cargarRegistroDiario(LocalDate.now())
    }

    fun onFechaSeleccionada(fecha: LocalDate) {
        if (fecha == _uiState.value.fechaSeleccionada) return
        _uiState.update { it.copy(fechaSeleccionada = fecha) }
        cargarRegistroDiario(fecha)
    }

    fun onPesoChange(valor: String) {
        _uiState.update { it.copy(peso = valor, errorPeso = null, mensajeError = null, mensajeExito = null) }
    }

    fun onCaloriasChange(valor: String) {
        _uiState.update { it.copy(calorias = valor, errorCalorias = null, mensajeError = null, mensajeExito = null) }
    }

    fun onSuenoChange(valor: String) {
        _uiState.update { it.copy(sueno = valor, errorSueno = null, mensajeError = null, mensajeExito = null) }
    }

    fun onPasosChange(valor: String) {
        _uiState.update { it.copy(pasos = valor, errorPasos = null, mensajeError = null, mensajeExito = null) }
    }

    private fun cargarRegistroDiario(fecha: LocalDate) {
        if (userId == null) {
            _uiState.update { it.copy(mensajeError = "Usuario no autenticado.") }
            return
        }
        val fechaString = fecha.format(DateTimeFormatter.ISO_DATE)

        _uiState.update { it.copy(isLoading = true, mensajeError = null, mensajeExito = null) }

        viewModelScope.launch {
            try {
                val querySnapshot = registrosCollection
                    .whereEqualTo("userId", userId)
                    .whereEqualTo("fecha", fechaString)
                    .limit(1)
                    .get()
                    .await()

                if (querySnapshot.isEmpty) {
                    // No hay registro: resetea los campos
                    _uiState.update {
                        it.copy(
                            registroId = null,
                            peso = "",
                            calorias = "",
                            sueno = "",
                            pasos = "",
                            isLoading = false
                        )
                    }
                } else {
                    // Encontró un registro: carga los datos en los campos String
                    val document = querySnapshot.documents.first()
                    val registro = document.toObject(RegistroDiario::class.java)
                    _uiState.update {
                        it.copy(
                            registroId = document.id,
                            peso = registro?.peso_kg?.toString() ?: "",
                            calorias = registro?.calorias_consumidas?.toString() ?: "",
                            sueno = registro?.horas_sueno?.toString() ?: "",
                            pasos = registro?.pasos?.toString() ?: "",
                            isLoading = false
                        )
                    }
                }

            } catch (e: Exception) {
                _uiState.update { it.copy(mensajeError = "Error al cargar datos: ${e.message}", isLoading = false) }
                e.printStackTrace()
            }
        }
    }

    fun guardarRegistro() {
        if (userId == null) {
            _uiState.update { it.copy(mensajeError = "Error: Usuario no autenticado.") }
            return
        }

        // 1. Validar los campos antes de guardar
        if (!validarFormulario()) {
            return // Detener si la validación falla
        }

        _uiState.update { it.copy(isLoading = true, mensajeError = null, mensajeExito = null) }

        // 2. Construir el objeto Modelo (RegistroDiario) desde los Strings
        val state = _uiState.value
        val registro = RegistroDiario(
            id = state.registroId, // ID del documento para saber si es 'set' o 'add'
            userId = userId,
            fecha = state.fechaSeleccionada.format(DateTimeFormatter.ISO_DATE),
            peso_kg = state.peso.toDoubleOrNull(),
            calorias_consumidas = state.calorias.toIntOrNull(),
            horas_sueno = state.sueno.toDoubleOrNull(),
            pasos = state.pasos.toIntOrNull()
        )

        // 3. Guardar en Firestore
        viewModelScope.launch {
            try {
                if (registro.id == null) {
                    // Crear nuevo registro
                    val result = registrosCollection.add(registro).await()
                    _uiState.update {
                        it.copy(
                            registroId = result.id, // Guardar el nuevo ID
                            mensajeExito = "Registro guardado exitosamente.",
                            isLoading = false
                        )
                    }
                } else {
                    // Actualizar registro existente
                    registrosCollection.document(registro.id).set(registro).await()
                    _uiState.update { it.copy(mensajeExito = "Registro actualizado exitosamente.", isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(mensajeError = "Error al guardar datos: ${e.message}", isLoading = false) }
                e.printStackTrace()
            }
        }
    }

    private fun validarFormulario(): Boolean {
        val state = _uiState.value
        var hayErrores = false

        // Limpiar errores antiguos
        _uiState.update { it.copy(errorPeso = null, errorCalorias = null, errorSueno = null, errorPasos = null) }

        // --- 1. Validación de Peso ---
        if (state.peso.isNotBlank()) {
            val pesoNum = state.peso.toDoubleOrNull()
            if (pesoNum == null) {
                _uiState.update { it.copy(errorPeso = "Número no válido") }
                hayErrores = true
            } else if (pesoNum <= 0) {
                _uiState.update { it.copy(errorPeso = "Debe ser un valor positivo") }
                hayErrores = true
            } else if (pesoNum > 400) { // <-- NUEVA REGLA
                _uiState.update { it.copy(errorPeso = "Valor irreal (max 400kg)") }
                hayErrores = true
            }
        }

        // --- 2. Validación de Calorías ---
        if (state.calorias.isNotBlank()) {
            val caloriasNum = state.calorias.toIntOrNull()
            if (caloriasNum == null) {
                _uiState.update { it.copy(errorCalorias = "Número no válido (entero)") }
                hayErrores = true
            } else if (caloriasNum < 0) {
                _uiState.update { it.copy(errorCalorias = "No puede ser negativo") }
                hayErrores = true
            } else if (caloriasNum > 20000) { // <-- NUEVA REGLA
                _uiState.update { it.copy(errorCalorias = "Valor irreal (max 20,000)") }
                hayErrores = true
            }
        }

        // --- 3. Validación de Sueño ---
        if (state.sueno.isNotBlank()) {
            val suenoNum = state.sueno.toDoubleOrNull()
            if (suenoNum == null) {
                _uiState.update { it.copy(errorSueno = "Número no válido") }
                hayErrores = true
            } else if (suenoNum <= 0) {
                _uiState.update { it.copy(errorSueno = "Debe ser un valor positivo") }
                hayErrores = true
            } else if (suenoNum > 24) { // (Regla ya existente)
                _uiState.update { it.copy(errorSueno = "Máximo 24 horas") }
                hayErrores = true
            }
        }

        // --- 4. Validación de Pasos ---
        if (state.pasos.isNotBlank()) {
            val pasosNum = state.pasos.toIntOrNull()
            if (pasosNum == null) {
                _uiState.update { it.copy(errorPasos = "Número no válido (entero)") }
                hayErrores = true
            } else if (pasosNum < 0) {
                _uiState.update { it.copy(errorPasos = "No puede ser negativo") }
                hayErrores = true
            } else if (pasosNum > 150000) {
                _uiState.update { it.copy(errorPasos = "Valor irreal (max 150,000)") }
                hayErrores = true
            }
        }

        return !hayErrores // Devuelve true si NO hay errores
    }

    fun clearMessages() {
        _uiState.update { it.copy(mensajeExito = null, mensajeError = null) }
    }
}