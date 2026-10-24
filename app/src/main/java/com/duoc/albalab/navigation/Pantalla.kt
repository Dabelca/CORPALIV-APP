package com.duoc.albalab.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

// Rutas de la app. Cada pantalla tiene su ruta, titulo e icono para el menu inferior
sealed class Pantalla(val ruta: String, val titulo: String, val icono: ImageVector) {
    object Inicio : Pantalla("inicio", "Inicio", Icons.Filled.Home)
    object Nosotros : Pantalla("nosotros", "Nosotros", Icons.Filled.Info)
    object Tienda : Pantalla("tienda", "Tienda", Icons.Filled.ShoppingCart)
    object Colaborar : Pantalla("colaborar", "Colaborar", Icons.Filled.Favorite)
    object Perfil : Pantalla("perfil", "Perfil", Icons.Filled.Person)
}

// Pantallas que aparecen en la barra de navegacion inferior
val pantallasMenu = listOf(
    Pantalla.Inicio,
    Pantalla.Nosotros,
    Pantalla.Tienda,
    Pantalla.Colaborar,
    Pantalla.Perfil
)
