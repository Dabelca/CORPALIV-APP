package com.duoc.albalab.data.remote

import com.duoc.albalab.model.IndicadoresResponse
import retrofit2.http.GET

interface IndicadoresApi {

    @GET("api")
    suspend fun obtenerIndicadores(): IndicadoresResponse
}