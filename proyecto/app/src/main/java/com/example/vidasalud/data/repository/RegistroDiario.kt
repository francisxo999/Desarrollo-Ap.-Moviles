package com.example.vidasalud.data.repository

import com.google.firebase.firestore.DocumentId

data class RegistroDiario(
    @DocumentId val id: String? = null,
    val userId: String = "",
    val fecha: String = "",
    val peso_kg: Double? = null,
    val calorias_consumidas: Int? = null,
    val horas_sueno: Double? = null,
    val pasos: Int? = null,
) {
    constructor() : this(
        id = null,
        userId = "",
        fecha = "",
        peso_kg = null,
        calorias_consumidas = null,
        horas_sueno = null,
        pasos = null
    )
}
