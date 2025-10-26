package com.example.vidasalud.ui.screens.estadisticas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vidasalud.ui.screens.estadisticas.components.TarjetaResumenEstadisticas
import com.example.vidasalud.ui.theme.VidaSaludTheme

data class DatoResumen(val icono: ImageVector, val titulo: String, val valor: String, val unidad: String)
val datosResumen = listOf(
    DatoResumen(Icons.Default.DirectionsWalk, "Distancia", "5.2", "km"),
    DatoResumen(Icons.Default.Timer, "Duración", "45", "min"),
    DatoResumen(Icons.Default.QueryStats, "Pasos", "7,500", ""),
    DatoResumen(Icons.Default.LocalFireDepartment, "Calorías", "350", "kcal"),
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaEstadisticas() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Datos", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { /* TODO: Mostrar selector de fecha */ }) {
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
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.shapes.medium),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Placeholder Gráfico", style = MaterialTheme.typography.bodyMedium)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Resumen del Entrenamiento",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(datosResumen) { dato ->
                        TarjetaResumenEstadisticas(
                            icono = dato.icono,
                            titulo = dato.titulo,
                            valor = dato.valor,
                            unidad = dato.unidad
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaEstadisticasPreview() {
    VidaSaludTheme {
        PantallaEstadisticas()
    }
}