package com.example.vidasalud.ui.screens.estadisticas.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.vidasalud.data.repository.RegistroDiario
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Fastfood

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioRegistro(
    registro: RegistroDiario,
    onValueChange: (campo: String, valor: Any?) -> Unit,
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

            OutlinedTextField(
                value = registro.peso_kg?.toString() ?: "",
                onValueChange = { newValue ->
                    val valueDouble = newValue.toDoubleOrNull()
                    onValueChange("peso", valueDouble)
                },
                label = { Text("Peso (kg)") },
                leadingIcon = { Icon(Icons.Default.MonitorWeight, contentDescription = "Peso") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = registro.calorias_consumidas?.toString() ?: "",
                onValueChange = { newValue ->
                    val valueInt = newValue.toIntOrNull()
                    onValueChange("calorias", valueInt)
                },
                label = { Text("Calorías Consumidas (kcal)") },
                leadingIcon = { Icon(Icons.Default.Fastfood, contentDescription = "Calorías") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = registro.horas_sueno?.toString() ?: "",
                onValueChange = { newValue ->
                    val valueDouble = newValue.toDoubleOrNull()
                    onValueChange("sueno", valueDouble)
                },
                label = { Text("Horas de Sueño") },
                leadingIcon = { Icon(Icons.Default.WbSunny, contentDescription = "Sueño") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = registro.pasos?.toString() ?: "",
                onValueChange = { newValue ->
                    val valueInt = newValue.toIntOrNull()
                    onValueChange("pasos", valueInt)
                },
                label = { Text("Pasos (Manual)") },
                leadingIcon = { Icon(Icons.Default.DirectionsRun, contentDescription = "Pasos") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

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