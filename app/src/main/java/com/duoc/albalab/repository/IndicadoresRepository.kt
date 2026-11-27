package com.duoc.albalab.repository

import com.duoc.albalab.data.remote.IndicadoresApi
import com.duoc.albalab.model.IndicadoresResponse

class IndicadoresRepository(private val api: IndicadoresApi) {

    suspend fun obtenerIndicadores(): IndicadoresResponse {
        return api.obtenerIndicadores()
    }
}