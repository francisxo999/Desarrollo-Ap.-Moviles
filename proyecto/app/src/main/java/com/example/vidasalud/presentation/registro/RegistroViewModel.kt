package com.example.vidasalud.presentation.registro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vidasalud.data.repository.RegistroDiario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RegistroViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val registrosCollection = db.collection("registros_diarios")

    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState: StateFlow<RegistroUiState> = _uiState

    init {
        cargarRegistroDiario(LocalDate.now())
    }

    fun onFechaSeleccionada(fecha: LocalDate) {
        _uiState.update { it.copy(fechaSeleccionada = fecha) }
        cargarRegistroDiario(fecha)
    }

    fun onRegistroChange(campo: String, valor: Any?) {
        _uiState.update { currentState ->
            val updatedRegistro = when (campo) {
                "peso" -> currentState.registroActual.copy(peso_kg = valor as? Double)
                "calorias" -> currentState.registroActual.copy(calorias_consumidas = valor as? Int)
                "sueno" -> currentState.registroActual.copy(horas_sueno = valor as? Double)
                "pasos" -> currentState.registroActual.copy(pasos = valor as? Int)
                else -> currentState.registroActual
            }
            currentState.copy(registroActual = updatedRegistro)
        }
    }

    private fun cargarRegistroDiario(fecha: LocalDate) {
        val userId = auth.currentUser?.uid ?: return
        val fechaString = fecha.format(DateTimeFormatter.ISO_DATE)

        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val querySnapshot = registrosCollection
                    .whereEqualTo("userId", userId)
                    .whereEqualTo("fecha", fechaString)
                    .limit(1)
                    .get()
                    .await()

                if (querySnapshot.isEmpty) {
                    _uiState.update {
                        it.copy(
                            registroActual = RegistroDiario(
                                userId = userId,
                                fecha = fechaString,
                                peso_kg = null,
                                calorias_consumidas = null,
                                horas_sueno = null,
                                pasos = null
                            ),
                            isLoading = false
                        )
                    }
                } else {
                    val document = querySnapshot.documents.first()
                    val registro = document.toObject(RegistroDiario::class.java)?.copy(id = document.id)
                    _uiState.update { it.copy(registroActual = registro ?: RegistroDiario(userId = userId, fecha = fechaString), isLoading = false) }
                }

            } catch (e: Exception) {
                _uiState.update { it.copy(mensajeError = "Error al cargar datos: ${e.message}", isLoading = false) }
                e.printStackTrace()
            }
        }
    }

    fun guardarRegistro() {
        val registro = _uiState.value.registroActual
        if (registro.userId.isEmpty()) {
            _uiState.update { it.copy(mensajeError = "Error: Usuario no autenticado.") }
            return
        }

        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                if (registro.id == null) {
                    val result = registrosCollection.add(registro).await()
                    _uiState.update {
                        it.copy(
                            registroActual = registro.copy(id = result.id),
                            mensajeExito = "Registro guardado exitosamente.",
                            isLoading = false
                        )
                    }
                } else {
                    registrosCollection.document(registro.id).set(registro).await()
                    _uiState.update { it.copy(mensajeExito = "Registro actualizado exitosamente.", isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(mensajeError = "Error al guardar datos: ${e.message}", isLoading = false) }
                e.printStackTrace()
            }
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(mensajeExito = null, mensajeError = null) }
    }
}