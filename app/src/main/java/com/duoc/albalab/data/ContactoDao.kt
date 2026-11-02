package com.duoc.albalab.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.duoc.albalab.model.Contacto

// Consultas a la tabla contactos
@Dao
interface ContactoDao {
    @Insert
    suspend fun insertar(contacto: Contacto)

    // solicitudes enviadas con ese correo
    @Query("SELECT * FROM contactos WHERE correo = :correo")
    suspend fun obtenerPorCorreo(correo: String): List<Contacto>
}
