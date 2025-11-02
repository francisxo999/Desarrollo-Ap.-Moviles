package com.example.vidasalud.ui.screens.estadisticas.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioRegistro(
    // Valores de los campos
    peso: String,
    calorias: String,
    sueno: String,
    pasos: String,
    // Errores de validación
    errorPeso: String?,
    errorCalorias: String?,
    errorSueno: String?,
    errorPasos: String?,
    // Funciones 'onChange' del ViewModel
    onPesoChange: (String) -> Unit,
    onCaloriasChange: (String) -> Unit,
    onSuenoChange: (String) -> Unit,
    onPasosChange: (String) -> Unit,
    // Acciones y estado
    onGuardarClick: () -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Ingresar Datos Diarios",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))

            // --- Campo de Peso ---
            OutlinedTextField(
                value = peso,
                onValueChange = onPesoChange, // Pasa el String directo al ViewModel
                label = { Text("Peso (kg)") },
                leadingIcon = { Icon(Icons.Default.MonitorWeight, contentDescription = "Peso") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                isError = errorPeso != null, // Se pone rojo si hay error
                supportingText = { // Muestra el mensaje de error
                    if (errorPeso != null) {
                        Text(text = errorPeso)
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // --- Campo de Calorías ---
            OutlinedTextField(
                value = calorias,
                onValueChange = onCaloriasChange,
                label = { Text("Calorías Consumidas (kcal)") },
                leadingIcon = { Icon(Icons.Default.Fastfood, contentDescription = "Calorías") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                isError = errorCalorias != null,
                supportingText = {
                    if (errorCalorias != null) {
                        Text(text = errorCalorias)
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // --- Campo de Sueño ---
            OutlinedTextField(
                value = sueno,
                onValueChange = onSuenoChange,
                label = { Text("Horas de Sueño") },
                leadingIcon = { Icon(Icons.Default.WbSunny, contentDescription = "Sueño") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                isError = errorSueno != null,
                supportingText = {
                    if (errorSueno != null) {
                        Text(text = errorSueno)
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // --- Campo de Pasos ---
            OutlinedTextField(
                value = pasos,
                onValueChange = onPasosChange,
                label = { Text("Pasos (Manual)") },
                leadingIcon = { Icon(Icons.Default.DirectionsRun, contentDescription = "Pasos") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                isError = errorPasos != null,
                supportingText = {
                    if (errorPasos != null) {
                        Text(text = errorPasos)
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            // --- Botón de Guardar ---
            Button(
                onClick = onGuardarClick,
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(Modifier.size(24.dp))
                } else {
                    Text("Guardar Registro")
                }
            }
        }
    }
}