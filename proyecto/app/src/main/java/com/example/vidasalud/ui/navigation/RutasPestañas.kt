package com.example.vidasalud.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class RutasPestañas(
    val ruta: String,
    val titulo: String,
    val icono: ImageVector
) {
    object Resumen : RutasPestañas(
        ruta = "resumen",
        titulo = "Resumen",
        icono = Icons.Default.Home
    )

    object Estadisticas : RutasPestañas(
        ruta = "estadisticas",
        titulo = "Datos",
        icono = Icons.Default.BarChart
    )
    object Comunidad : RutasPestañas(
        ruta = "comunidad",
        titulo = "Comunidad",
        icono = Icons.Default.People
    )
    object Perfil : RutasPestañas(
        ruta = "perfil",
        titulo = "Perfil",
        icono = Icons.Default.Person
    )
}