package com.duoc.albalab.model

// estado del form de contacto
data class ContactoUiState(
    val nombre: String = "",
    val correo: String = "",
    val mensaje: String = "",
    val tipoColaborador: String = "Persona",
    val formaColaboracion: String = "",
    val enviado: Boolean = false,
    val guardando: Boolean = false,
    val errores: ContactoErrores = ContactoErrores()
)

// errores de cada campo, null = sin error
data class ContactoErrores(
    val nombre: String? = null,
    val correo: String? = null,
    val mensaje: String? = null
)
