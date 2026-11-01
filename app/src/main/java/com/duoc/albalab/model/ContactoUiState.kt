package com.duoc.albalab.model

// Estado del formulario de contacto de "Quiero ayudar"
data class ContactoUiState(
    val nombre: String = "",
    val correo: String = "",
    val mensaje: String = "",
    val enviado: Boolean = false,
    val errores: ContactoErrores = ContactoErrores()
)

// Errores de cada campo, null = sin error
data class ContactoErrores(
    val nombre: String? = null,
    val correo: String? = null,
    val mensaje: String? = null
)
