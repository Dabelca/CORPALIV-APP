package com.duoc.albalab.ui.tienda

import androidx.compose.runtime.Composable
import com.duoc.albalab.ui.components.PantallaEnConstruccion

//Catalogo de productos con filtro y favoritos
@Composable
fun PantallaTienda() {
    PantallaEnConstruccion(
        titulo = "Tienda",
        descripcion = "Aqui veras los productos hechos en Alba Lab"
    )
}
