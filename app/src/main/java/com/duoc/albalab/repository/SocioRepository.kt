package com.duoc.albalab.repository

import com.duoc.albalab.data.remote.ApiService
import com.duoc.albalab.data.remote.RetrofitInstance
import com.duoc.albalab.model.Socio

// intermedio entre el viewModel y el microservicio
class SocioRepository(private val api: ApiService = RetrofitInstance.api) {

    suspend fun login(correo: String, clave: String): Socio {
        return api.login(Socio(correo = correo, clave = clave))
    }

    suspend fun registrar(socio: Socio): Socio {
        return api.registrar(socio)
    }

    suspend fun actualizar(id: Int, socio: Socio): Socio {
        return api.actualizar(id, socio)
    }

    suspend fun eliminar(id: Int) {
        api.eliminar(id)
    }
}
