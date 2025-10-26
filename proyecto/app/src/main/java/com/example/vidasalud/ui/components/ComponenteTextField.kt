package com.example.vidasalud.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.vidasalud.ui.theme.BotonOscuro


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComponenteTextField(
    valor: String,
    enValorCambiado: (String) -> Unit,
    etiqueta: String,
    esContrasena: Boolean = false
) {
    OutlinedTextField(
        value = valor,
        onValueChange = enValorCambiado,
        label = { Text(etiqueta) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = BotonOscuro,
            focusedLabelColor = BotonOscuro,
            cursorColor = BotonOscuro,
            containerColor = Color.White
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = if (esContrasena) KeyboardType.Password else KeyboardType.Email
        ),
        visualTransformation = if (esContrasena) PasswordVisualTransformation() else VisualTransformation.None
    )
}