package com.example.vidasalud.presentation.perfil

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vidasalud.data.repository.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PerfilViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("usuarios")
    private val userId = auth.currentUser?.uid

    private val _uiState = MutableStateFlow(PerfilUiState(isLoading = true))
    val uiState: StateFlow<PerfilUiState> = _uiState

    init {
        if (userId != null) {
            cargarPerfil(userId)
        } else {
            _uiState.update { it.copy(isLoading = false, mensajeError = "Usuario no autenticado.") }
        }
    }

    private fun cargarPerfil(uid: String) {
        viewModelScope.launch {
            try {
                val document = usersCollection.document(uid).get().await()
                val firestoreUser = document.toObject(Usuario::class.java)
                val authEmail = auth.currentUser?.email
                val finalUser = firestoreUser?.copy(
                    id = document.id,
                    uid = uid,
                    correo = authEmail
                ) ?: Usuario(
                    uid = uid,
                    correo = authEmail
                )

                _uiState.update { it.copy(currentUser = finalUser, isLoading = false) }

            } catch (e: Exception) {
                _uiState.update { it.copy(mensajeError = "Error al cargar perfil: ${e.message}", isLoading = false) }
                Log.e("PerfilViewModel", "Error al cargar perfil", e)
            }
        }
    }

    fun actualizarFotoPerfil(uriString: String?) {
        _uiState.update { currentState ->
            val updatedUser = currentState.currentUser?.copy(fotoPerfilUrl = uriString)
            currentState.copy(currentUser = updatedUser)
        }
        guardarPerfil()
    }

    fun guardarPerfil() {
        val userToSave = _uiState.value.currentUser ?: return

        viewModelScope.launch {
            try {
                usersCollection.document(userToSave.uid).set(userToSave).await()
                Log.d("PerfilViewModel", "Perfil guardado/actualizado exitosamente.")
            } catch (e: Exception) {
                Log.e("PerfilViewModel", "Error al guardar perfil", e)
            }
        }
    }
}