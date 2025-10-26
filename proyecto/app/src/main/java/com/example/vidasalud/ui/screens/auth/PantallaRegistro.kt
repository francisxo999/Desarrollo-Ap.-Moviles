package com.example.vidasalud.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.vidasalud.ui.components.ComponenteTextField
import com.example.vidasalud.ui.navigation.RutasApp
import com.example.vidasalud.ui.theme.BotonOscuro
import com.example.vidasalud.ui.theme.VidaSaludTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaRegistro(controladorNavegacion: NavController) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            // Barra superior con botón de "atrás"
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = {
                        controladorNavegacion.popBackStack() // Vuelve a la pantalla anterior (Login)
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver a Login"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), // Aplica el padding de Scaffold
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Crea tu Cuenta",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        textAlign = TextAlign.Center
                    )
                )
                Text(
                    text = "Únete a la comunidad de VidaSalud",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(48.dp))

                // Campo de Texto para Nombre
                ComponenteTextField(
                    valor = nombre,
                    enValorCambiado = { nombre = it },
                    etiqueta = "Nombre",
                    esContrasena = false
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de Texto para Email
                ComponenteTextField(
                    valor = email,
                    enValorCambiado = { email = it },
                    etiqueta = "Email",
                    esContrasena = false
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de Texto para Contraseña
                ComponenteTextField(
                    valor = contrasena,
                    enValorCambiado = { contrasena = it },
                    etiqueta = "Contraseña",
                    esContrasena = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de Texto para Confirmar Contraseña
                ComponenteTextField(
                    valor = confirmarContrasena,
                    enValorCambiado = { confirmarContrasena = it },
                    etiqueta = "Confirmar Contraseña",
                    esContrasena = true
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Botón de Registrarse
                Button(
                    onClick = {
                        // Lógica de registro (simulada)
                        controladorNavegacion.navigate(RutasApp.PantallaPrincipal.ruta) {
                            popUpTo(RutasApp.PantallaBienvenida.ruta) {
                                inclusive = true
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BotonOscuro,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = "Crear Cuenta",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Fila para el botón de "Ya tengo cuenta"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "¿Ya tienes una cuenta?",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    TextButton(onClick = {
                        controladorNavegacion.popBackStack() // Vuelve a Login
                    }) {
                        Text(
                            text = "Inicia Sesión",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PantallaRegistroPreview() {
    VidaSaludTheme {
        PantallaRegistro(controladorNavegacion = rememberNavController())
    }
}

