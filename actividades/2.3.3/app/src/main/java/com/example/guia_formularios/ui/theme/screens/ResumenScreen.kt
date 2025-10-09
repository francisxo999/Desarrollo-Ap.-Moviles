package com.example.guia_formularios.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.guia_formularios.viewmodel.UsuarioViewModel

@Composable
fun ResumenScreen(viewModel: UsuarioViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Resumen del Usuario", style = MaterialTheme.typography.titleLarge)
        HorizontalDivider()
        Text("Nombre: ${uiState.nombre}")
        Text("Correo: ${uiState.correo}")
        Text("Dirección: ${uiState.direccion}")
        Text("Acepta términos: ${if (uiState.aceptaTerminos) "Sí" else "No"}")
    }
}
