package com.example.appadaptable_grupo7.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

enum class DeviceWindowSize { Compact, Medium, Expanded }

/**
 * Determina el tipo de pantalla según el ancho (en dp)
 */
@Composable
fun rememberWindowWidthSizeClass(): DeviceWindowSize {
    val configuration = LocalConfiguration.current
    val widthDp = configuration.screenWidthDp
    return when {
        widthDp < 600 -> DeviceWindowSize.Compact
        widthDp < 840 -> DeviceWindowSize.Medium
        else -> DeviceWindowSize.Expanded
    }
}
