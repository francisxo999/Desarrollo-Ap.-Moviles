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
fun HomeScreenExpandida(homeViewModel: HomeViewModel = viewModel()) {
    val mensaje by homeViewModel.mensaje.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        Row(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(40.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = mensaje, style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar("Estás en modo Expandido 🖥️")
                    }
                }) {
                    Text("Modo Expandido 🖥️")
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 1000, heightDp = 800)
@Composable
fun PreviewExpandida() = HomeScreenExpandida()
