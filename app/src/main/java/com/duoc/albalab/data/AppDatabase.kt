package com.duoc.albalab.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.duoc.albalab.model.Contacto

// Base de datos local (SQLite con Room)
@Database(entities = [Contacto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun contactoDao(): ContactoDao

    companion object {
        @Volatile
        private var instancia: AppDatabase? = null

        // Crea la base de datos una sola vez
        fun obtener(context: Context): AppDatabase {
            return instancia ?: synchronized(this) {
                instancia ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "albalab.db"
                ).build().also { instancia = it }
            }
        }
    }
}
