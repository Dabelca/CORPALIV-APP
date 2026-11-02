package com.duoc.albalab.model

// estado del form de login
data class LoginUiState(
    val correo: String = "",
    val clave: String = "",
    val nombreSocio: String? = null, // null = no ha iniciado sesion
    val errores: LoginErrores = LoginErrores()
)

// errores de cada campo, null = sin error
data class LoginErrores(
    val correo: String? = null,
    val clave: String? = null
)
