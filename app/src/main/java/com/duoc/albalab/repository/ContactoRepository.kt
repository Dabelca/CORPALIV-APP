package com.duoc.albalab.repository

import com.duoc.albalab.data.ContactoDao
import com.duoc.albalab.model.Contacto

// Intermediario entre el ViewModel y la base de datos
class ContactoRepository(private val dao: ContactoDao) {

    suspend fun guardar(contacto: Contacto) {
        dao.insertar(contacto)
    }
}
