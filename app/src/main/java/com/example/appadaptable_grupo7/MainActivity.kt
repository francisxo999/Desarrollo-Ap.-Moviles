package com.example.appadaptable_grupo7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.appadaptable_grupo7.ui.screens.*
import com.example.appadaptable_grupo7.ui.theme.AppAdaptable_Grupo7Theme
import com.example.appadaptable_grupo7.ui.utils.DeviceWindowSize
import com.example.appadaptable_grupo7.ui.utils.rememberWindowWidthSizeClass

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppAdaptable_Grupo7Theme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val windowSize = rememberWindowWidthSizeClass()
                    when (windowSize) {
                        DeviceWindowSize.Compact -> HomeScreenCompacta()
                        DeviceWindowSize.Medium -> HomeScreenMediana()
                        DeviceWindowSize.Expanded -> HomeScreenExpandida()
                    }
                }
            }
        }
    }
}
