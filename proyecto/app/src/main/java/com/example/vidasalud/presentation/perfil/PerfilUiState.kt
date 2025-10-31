package com.example.vidasalud.presentation.perfil

import com.example.vidasalud.data.repository.Usuario

data class PerfilUiState(
    val currentUser: Usuario? = null,
    val isLoading: Boolean = false,
    val mensajeError: String? = null,
)