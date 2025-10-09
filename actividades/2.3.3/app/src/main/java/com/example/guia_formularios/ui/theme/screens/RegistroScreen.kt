package com.example.guia_formularios.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import com.example.guia_formularios.viewmodel.UsuarioViewModel

@Composable
fun RegistroScreen(navController: NavController, viewModel: UsuarioViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val errores by viewModel.errores.collectAsState()
    var mostrarClave by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        OutlinedTextField(
            value = uiState.nombre,
            onValueChange = { viewModel.updateNombre(it) },
            label = { Text("Nombre") },
            isError = errores.nombre != null,
            supportingText = { errores.nombre?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = uiState.correo,
            onValueChange = { viewModel.updateCorreo(it) },
            label = { Text("Correo") },
            isError = errores.correo != null,
            supportingText = { errores.correo?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = uiState.clave,
            onValueChange = { viewModel.updateClave(it) },
            label = { Text("Clave") },
            visualTransformation = if (mostrarClave) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { mostrarClave = !mostrarClave }) {
                    Icon(
                        imageVector = if (mostrarClave) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = null
                    )
                }
            },
            isError = errores.clave != null,
            supportingText = { errores.clave?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = uiState.direccion,
            onValueChange = { viewModel.updateDireccion(it) },
            label = { Text("Dirección") },
            isError = errores.direccion != null,
            supportingText = { errores.direccion?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )

        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Checkbox(
                checked = uiState.aceptaTerminos,
                onCheckedChange = { viewModel.toggleAceptaTerminos() }
            )
            Text("Acepto términos y condiciones")
        }

        Button(
            onClick = {
                if (viewModel.validarFormulario()) {
                    navController.navigate("resumen")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar")
        }
    }
}
