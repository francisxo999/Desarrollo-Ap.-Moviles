package com.example.appadaptable_grupo7.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val _mensaje = MutableStateFlow("¡Bienvenido a la App Adaptable!")
    val mensaje = _mensaje.asStateFlow()
}
