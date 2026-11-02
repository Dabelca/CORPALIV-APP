package com.duoc.albalab.data

import androidx.room.Dao
import androidx.room.Insert
import com.duoc.albalab.model.Contacto

// Consultas a la tabla contactos
@Dao
interface ContactoDao {
    @Insert
    suspend fun insertar(contacto: Contacto)
}
