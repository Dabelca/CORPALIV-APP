package com.duoc.albalab.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Tabla de la base de datos con los formularios enviados
@Entity(tableName = "contactos")
data class Contacto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val correo: String,
    val mensaje: String
)
