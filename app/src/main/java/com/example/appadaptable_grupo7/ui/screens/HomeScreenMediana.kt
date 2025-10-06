package com.example.appadaptable_grupo7.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appadaptable_grupo7.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenMediana(homeViewModel: HomeViewModel = viewModel()) {
    val mensaje by homeViewModel.mensaje.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        Row(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = mensaje, style = MaterialTheme.typography.headlineMedium)
            Button(onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar("Estás en modo Mediano 💻")
                }
            }) {
                Text("Modo Mediano 💻")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 700, heightDp = 1024)
@Composable
fun PreviewMediana() = HomeScreenMediana()
