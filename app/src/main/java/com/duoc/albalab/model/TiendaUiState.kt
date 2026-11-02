package com.duoc.albalab.model

data class TiendaUiState(

    val productos: List<Producto> = emptyList(),
    val tipos: List<String> = emptyList(),
    val tipoSeleccionado: String? = null
)
