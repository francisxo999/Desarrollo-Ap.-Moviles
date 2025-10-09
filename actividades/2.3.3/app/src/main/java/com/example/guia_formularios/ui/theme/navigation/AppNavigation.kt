package com.example.guia_formularios.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.guia_formularios.ui.theme.screens.*
import com.example.guia_formularios.viewmodel.UsuarioViewModel

@Composable
fun AppNavigation(viewModel: UsuarioViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "registro") {
        composable("registro") {
            RegistroScreen(navController = navController, viewModel = viewModel)
        }
        composable("resumen") {
            ResumenScreen(viewModel = viewModel)
        }
    }
}
