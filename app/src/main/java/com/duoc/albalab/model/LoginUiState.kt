package com.duoc.albalab.model

import android.net.Uri

// estado del form de login y registro
data class LoginUiState(
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val confirmarClave: String = "",
    val registrando: Boolean = false, // true = muestra el form de registro
    val registroExitoso: Boolean = false, // true = muestra el mensaje de exito
    val nombreSocio: String? = null, // null = no ha iniciado sesion
    val solicitudes: List<Contacto> = emptyList(), // solicitudes enviadas por el socio
    val fotoPerfil: Uri? = null, // null = sin foto
    val errores: LoginErrores = LoginErrores()
)

// errores de cada campo, null = sin error
data class LoginErrores(
    val nombre: String? = null,
    val correo: String? = null,
    val clave: String? = null,
    val confirmarClave: String? = null
)
