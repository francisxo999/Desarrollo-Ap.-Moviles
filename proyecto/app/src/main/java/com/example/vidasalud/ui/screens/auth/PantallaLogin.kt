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
    import androidx.compose.material3.Button
    import androidx.compose.material3.ButtonDefaults
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.Surface
    import androidx.compose.material3.Text
    import androidx.compose.material3.TextButton
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

    @Composable
    fun PantallaLogin(controladorNavegacion: NavController) {
        var email by remember { mutableStateOf("") }
        var contrasena by remember { mutableStateOf("") }

        Surface(
            modifier = Modifier.fillMaxSize(),
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
                    text = "¡Bienvenido de Nuevo!",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        textAlign = TextAlign.Center
                    )
                )
                Text(
                    text = "Inicia sesión para continuar",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(48.dp))

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

                Spacer(modifier = Modifier.height(32.dp))

                // Botón de Iniciar Sesión
                Button(
                    onClick = {
                        // Lógica de inicio de sesión (simulada)
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
                        text = "Iniciar Sesión",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Fila para el botón de registro
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "¿No tienes una cuenta?",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    TextButton(onClick = {
                        controladorNavegacion.navigate(RutasApp.PantallaRegistro.ruta)
                    }) {
                        Text(
                            text = "Regístrate",
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

    @Preview(showBackground = true)
    @Composable
    fun PantallaLoginPreview() {
        VidaSaludTheme {
            PantallaLogin(controladorNavegacion = rememberNavController())
        }
    }

