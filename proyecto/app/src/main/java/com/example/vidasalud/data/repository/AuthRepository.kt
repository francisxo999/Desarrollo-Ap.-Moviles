package com.example.vidasalud.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.lang.Exception

sealed class ResultadoAuth {
    object Exito : ResultadoAuth()
    data class Error(val mensaje: String) : ResultadoAuth()
}

class AuthRepository {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun iniciarSesion(email: String, contrasena: String): ResultadoAuth {
        return try {
            auth.signInWithEmailAndPassword(email, contrasena).await()
            ResultadoAuth.Exito
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error en iniciarSesion: ${e.message}")
            ResultadoAuth.Error(e.message ?: "Error desconocido al iniciar sesión")
        }
    }

    suspend fun crearUsuario(nombre: String, email: String, contrasena: String): ResultadoAuth {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, contrasena).await()
            val uid = authResult.user?.uid

            if (uid != null) {
                val datosUsuario = hashMapOf(
                    "uid" to uid,
                    "nombre" to nombre,
                    "email" to email
                )
                firestore.collection("usuarios").document(uid)
                    .set(datosUsuario)
                    .await()

                ResultadoAuth.Exito
            } else {
                ResultadoAuth.Error("No se pudo crear el usuario (UID nulo)")
            }

        } catch (e: Exception) {
            Log.e("AuthRepository", "Error en crearUsuario: ${e.message}")
            val mensajeError = when {
                e.message?.contains("EMAIL_EXISTS") == true || e.message?.contains("email-already-in-use") == true -> "El email ya está en uso."
                else -> e.message ?: "Error desconocido al registrar"
            }
            ResultadoAuth.Error(mensajeError)
        }
    }

    suspend fun obtenerNombreUsuario(): String? {
        val uid = auth.currentUser?.uid ?: return null

        return try {
            val documento = firestore.collection("usuarios").document(uid).get().await()

            documento.getString("nombre")
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error al obtener nombre de usuario: ${e.message}")
            null
        }
    }
}