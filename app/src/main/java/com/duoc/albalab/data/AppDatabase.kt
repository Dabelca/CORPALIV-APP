package com.duoc.albalab.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.duoc.albalab.model.Contacto
import com.duoc.albalab.model.Socio
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Base de datos local (SQLite con Room)
@Database(entities = [Contacto::class, Socio::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun contactoDao(): ContactoDao

    abstract fun socioDao(): SocioDao

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
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // solo la primera vez: crea un socio de prueba (datos ficticios)
                            CoroutineScope(Dispatchers.IO).launch {
                                nueva.socioDao().insertar(
                                    Socio(nombre = "Socio Prueba", correo = "socio@prueba.cl", clave = "123456")
                                )
                            }
                        }
                    })
                    .build()
                instancia = nueva
                nueva
            }
        }
    }
}
