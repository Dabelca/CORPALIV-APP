package com.duoc.albalab.model

data class Indicador(
    val codigo: String,
    val nombre: String,
    val valor: Double
)

data class IndicadoresResponse(

    val uf: Indicador,
    val utm: Indicador
)