package com.duoc.albalab.model

// datos de un socio del microservicio
data class Socio(
    val id: Int? = null,
    val nombre: String = "",
    val correo: String,
    val clave: String
)
