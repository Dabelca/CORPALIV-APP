package com.duoc.albalab.data.remote

import com.duoc.albalab.model.Socio
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

// endpoints del microservicio de socios
interface ApiService {

    @POST("api/socios")
    suspend fun registrar(@Body socio: Socio): Socio

    @POST("api/socios/login")
    suspend fun login(@Body socio: Socio): Socio

    @PUT("api/socios/{id}")
    suspend fun actualizar(@Path("id") id: Int, @Body socio: Socio): Socio

    @DELETE("api/socios/{id}")
    suspend fun eliminar(@Path("id") id: Int)
}
