package com.duoc.albalab.model

import androidx.annotation.DrawableRes

data class Producto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Int,
    val tipo: String,
    @DrawableRes val imagen: Int
)
