package com.duoc.albalab.repository

import com.duoc.albalab.data.SocioDao
import com.duoc.albalab.model.Socio

// Intermedio entre el viewModel y la bd
class SocioRepository(private val dao: SocioDao) {

    suspend fun buscar(correo: String, clave: String): Socio? {
        return dao.buscar(correo, clave)
    }

    suspend fun insertar(socio: Socio) {
        dao.insertar(socio)
    }
}
