package com.duoc.albalab.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.duoc.albalab.model.Contacto

// Base de datos local (SQLite con Room)
@Database(entities = [Contacto::class], version = 3)
abstract class AppDatabase : RoomDatabase() {

    abstract fun contactoDao(): ContactoDao

    companion object {
        @Volatile
        private var instancia: AppDatabase? = null

        // Crea la base de datos una sola vez
        fun obtener(context: Context): AppDatabase {
            return instancia ?: synchronized(this) {
                lateinit var nueva: AppDatabase
                nueva = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "albalab.db"
                )
                    // si cambia la version borra y crea la bd de nuevo
                    .fallbackToDestructiveMigration()
                    .build()
                instancia = nueva
                nueva
            }
        }
    }
}
