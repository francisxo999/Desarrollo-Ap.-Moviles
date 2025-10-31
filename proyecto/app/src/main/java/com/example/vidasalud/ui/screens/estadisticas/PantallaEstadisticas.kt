package com.example.vidasalud.ui.screens.estadisticas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vidasalud.presentation.registro.RegistroViewModel
import com.example.vidasalud.ui.screens.estadisticas.components.FormularioRegistro
import com.example.vidasalud.ui.screens.estadisticas.components.TarjetaResumenEstadisticas
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

data class DatoResumen(val icono: ImageVector, val titulo: String, val valor: String, val unidad: String)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaEstadisticas(
    viewModel: RegistroViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val showDatePicker = remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = uiState.fechaSeleccionada
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    )

    LaunchedEffect(uiState.mensajeExito, uiState.mensajeError) {
        val mensaje = uiState.mensajeExito ?: uiState.mensajeError
        mensaje?.let {
            snackbarHostState.showSnackbar(message = it, duration = SnackbarDuration.Short)
            viewModel.clearMessages()
        }
    }

    if (showDatePicker.value) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val selectedDate = Instant.ofEpochMilli(datePickerState.selectedDateMillis ?: 0L)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        viewModel.onFechaSeleccionada(selectedDate)
                        showDatePicker.value = false
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker.value = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    val registro = uiState.registroActual
    val datosResumenDinamicos = remember(registro) {
        listOf(
            DatoResumen(Icons.Default.DirectionsWalk, "Pasos", registro.pasos?.toString() ?: "0", ""),
            DatoResumen(Icons.Default.LocalFireDepartment, "Calorías", registro.calorias_consumidas?.toString() ?: "0", "kcal"),
            DatoResumen(Icons.Default.MonitorWeight, "Peso", registro.peso_kg?.toString() ?: "0.0", "kg"),
            DatoResumen(Icons.Default.Bedtime, "Sueño", registro.horas_sueno?.toString() ?: "0.0", "h"),
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Datos", fontWeight = FontWeight.Bold) },
                actions = {
                    Text(
                        text = uiState.fechaSeleccionada.format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = { showDatePicker.value = true }) {
                        Icon(Icons.Default.CalendarToday, contentDescription = "Seleccionar Fecha")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.shapes.medium),
                    contentAlignment = Alignment.Center
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator()
                    } else {
                        Text("Placeholder Gráfico", style = MaterialTheme.typography.bodyMedium)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Resumen del Día",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(datosResumenDinamicos) { dato ->
                        TarjetaResumenEstadisticas(
                            icono = dato.icono,
                            titulo = dato.titulo,
                            valor = dato.valor,
                            unidad = dato.unidad
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                FormularioRegistro(
                    registro = uiState.registroActual,
                    onValueChange = viewModel::onRegistroChange,
                    onGuardarClick = viewModel::guardarRegistro,
                    isLoading = uiState.isLoading,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}