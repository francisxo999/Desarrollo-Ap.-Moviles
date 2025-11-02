package com.example.vidasalud.ui.screens.estadisticas

import android.os.Build
import androidx.annotation.RequiresApi
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


@RequiresApi(Build.VERSION_CODES.O)
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

    // CORREGIDO: (Error 1)
    // Ya no existe 'registroActual'. Leemos los campos de 'uiState' directamente.
    // Usamos '.ifEmpty' para mostrar un valor por defecto.
    val datosResumenDinamicos = remember(uiState.pasos, uiState.calorias, uiState.peso, uiState.sueno) {
        listOf(
            DatoResumen(Icons.Default.DirectionsWalk, "Pasos", uiState.pasos.ifEmpty { "0" }, ""),
            DatoResumen(Icons.Default.LocalFireDepartment, "Calorías", uiState.calorias.ifEmpty { "0" }, "kcal"),
            DatoResumen(Icons.Default.MonitorWeight, "Peso", uiState.peso.ifEmpty { "0.0" }, "kg"),
            DatoResumen(Icons.Default.Bedtime, "Sueño", uiState.sueno.ifEmpty { "0.0" }, "h"),
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

                // CORREGIDO: (Errores 2 y 3)
                // Ya no pasamos 'registroActual' ni 'onRegistroChange'.
                // Ahora pasamos todos los valores, errores y funciones
                // individualmente desde el ViewModel y el UiState.
                FormularioRegistro(
                    // Valores
                    peso = uiState.peso,
                    calorias = uiState.calorias,
                    sueno = uiState.sueno,
                    pasos = uiState.pasos,
                    // Errores
                    errorPeso = uiState.errorPeso,
                    errorCalorias = uiState.errorCalorias,
                    errorSueno = uiState.errorSueno,
                    errorPasos = uiState.errorPasos,
                    // Funciones del ViewModel
                    onPesoChange = viewModel::onPesoChange,
                    onCaloriasChange = viewModel::onCaloriasChange,
                    onSuenoChange = viewModel::onSuenoChange,
                    onPasosChange = viewModel::onPasosChange,
                    // Acciones
                    onGuardarClick = viewModel::guardarRegistro,
                    isLoading = uiState.isLoading,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}