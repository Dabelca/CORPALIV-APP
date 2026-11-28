package com.duoc.albalab.repository

import com.duoc.albalab.data.remote.SolicitudApi
import com.duoc.albalab.data.remote.SolicitudInstance
import com.duoc.albalab.model.Solicitud

class ContactoRepository(private val api: SolicitudApi = SolicitudInstance.api){

    suspend fun crear(solicitud: Solicitud): Solicitud{
        return api.crear(solicitud)
    }

    suspend fun obtenerPorCorreo(correo: String): List<Solicitud>{
        return api.listarPorCorreo(correo)
    }

    suspend fun actualizar(id: Long, solicitud: Solicitud): Solicitud{
        return api.actualizar(id, solicitud)
    }

    suspend fun eliminar(id: Long){
        api.eliminar(id)
    }
}