package com.duoc.albalab.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.duoc.albalab.model.Socio

// Consultas a la tabla socios
@Dao
interface SocioDao {
    @Insert
    suspend fun insertar(socio: Socio)

    // busca un socio con ese correo y clave, null si no existe
    @Query("SELECT * FROM socios WHERE correo = :correo AND clave = :clave")
    suspend fun buscar(correo: String, clave: String): Socio?
}
