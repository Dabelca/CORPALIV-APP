package com.duoc.albalab.ui.perfil

import androidx.compose.runtime.Composable
import com.duoc.albalab.ui.components.PantallaEnConstruccion

// registro, login, mis solicitudes y sjustes
@Composable
fun PantallaPerfil() {
    PantallaEnConstruccion(
        titulo = "Mi perfil",
        descripcion = "Aqui podras iniciar sesion y ver tus solicitudes "
    )
}
