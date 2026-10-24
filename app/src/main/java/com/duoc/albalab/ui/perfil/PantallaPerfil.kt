package com.duoc.albalab.ui.perfil

import androidx.compose.runtime.Composable
import com.duoc.albalab.ui.components.PantallaEnConstruccion

// RF06, RF08, RF12 y RNF01: registro, login, mis solicitudes y ajustes
@Composable
fun PantallaPerfil() {
    PantallaEnConstruccion(
        titulo = "Mi perfil",
        descripcion = "Aqui podras iniciar sesion, ver tus solicitudes y cambiar el tamaño de letra."
    )
}
