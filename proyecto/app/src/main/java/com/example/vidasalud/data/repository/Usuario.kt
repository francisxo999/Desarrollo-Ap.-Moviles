package com.example.vidasalud.data.repository

import com.google.firebase.firestore.DocumentId

data class Usuario(
    @DocumentId val id: String? = null,
    val uid: String = "",
    val nombre: String? = null,
    val correo: String? = null,
    val fotoPerfilUrl: String? = null,
) {
    constructor() : this(
        id = null,
        uid = "",
        nombre = null,
        correo = null,
        fotoPerfilUrl = null
    )
}