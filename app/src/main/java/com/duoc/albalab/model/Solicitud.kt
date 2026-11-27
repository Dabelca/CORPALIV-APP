package com.duoc.albalab.model

data class Solicitud(
    val id: Long? = null,
    val nombre: String,
    val correo: String,
    val tipoColaborador: String,
    val formaColaboracion: String,
    val mensaje: String,
    val estado: String? = null
)
