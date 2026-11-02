package com.duoc.albalab.repository

import com.duoc.albalab.data.ContactoDao
import com.duoc.albalab.model.Contacto

// Intermedio entre el viewModel y la bd
class ContactoRepository(private val dao: ContactoDao) {

    suspend fun guardar(contacto: Contacto) {
        dao.insertar(contacto)
    }

    suspend fun obtenerPorCorreo(correo: String): List<Contacto> {
        return dao.obtenerPorCorreo(correo)
    }
}
