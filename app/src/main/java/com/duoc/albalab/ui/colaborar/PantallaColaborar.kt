package com.duoc.albalab.ui.colaborar

import androidx.compose.runtime.Composable
import com.duoc.albalab.ui.components.PantallaEnConstruccion

// RF05 y RF07: simulador de aporte y formulario "Quiero colaborar"
@Composable
fun PantallaColaborar() {
    PantallaEnConstruccion(
        titulo = "Quiero colaborar",
        descripcion = "Aqui podras donar, ser voluntario o crear una alianza."
    )
}
