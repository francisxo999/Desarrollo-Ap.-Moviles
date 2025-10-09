package com.example.guia_formularios

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.MaterialTheme
import com.example.guia_formularios.ui.theme.navigation.AppNavigation
import com.example.guia_formularios.viewmodel.UsuarioViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val usuarioViewModel: UsuarioViewModel = viewModel()
            MaterialTheme {
                AppNavigation(viewModel = usuarioViewModel)
            }
        }
    }
}
