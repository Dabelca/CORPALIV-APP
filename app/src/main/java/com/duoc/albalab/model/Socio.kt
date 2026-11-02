package com.duoc.albalab.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// tabla de la bd con los socios que pueden iniciar sesion
@Entity(tableName = "socios")
data class Socio(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val correo: String,
    val clave: String
)
