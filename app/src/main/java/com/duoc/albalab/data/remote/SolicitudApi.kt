package com.duoc.albalab.data.remote

import com.duoc.albalab.model.Solicitud
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface SolicitudApi {

    @POST("api/solicitudes")
    suspend fun crear(@Body solicitud: Solicitud): Solicitud

    @GET("api/solicitudes")
    suspend fun listarPorCorreo(@Query("correo") correo: String): List<Solicitud>

    @PUT("api/solicitudes/{id}")
    suspend fun actualizar(@Path("id") id: Long, @Body solicitud: Solicitud): Solicitud

    @DELETE("api/solicitudes/{id}")
    suspend fun eliminar(@Path("id") id: Long)


}