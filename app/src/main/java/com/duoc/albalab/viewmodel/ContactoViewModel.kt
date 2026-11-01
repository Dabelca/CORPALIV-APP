package com.duoc.albalab.viewmodel

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.albalab.data.AppDatabase
import com.duoc.albalab.model.Contacto
import com.duoc.albalab.model.ContactoErrores
import com.duoc.albalab.model.ContactoUiState
import com.duoc.albalab.repository.ContactoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// logica y validaciones del formulario de contacto
class ContactoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ContactoRepository(AppDatabase.obtener(application).contactoDao())

// logica y validaciones del formulario de contacto
class ContactoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository =
        ContactoRepository(AppDatabase.obtener(application).contactoDao())

    private val _estado = MutableStateFlow(ContactoUiState())
    val estado: StateFlow<ContactoUiState> = _estado

    // cada cambio actualiza el campo y limpia su error
    fun onNombreChange(valor: String) {
        _estado.update {
            it.copy(
                nombre = valor,
                enviado = false,
                errores = it.errores.copy(nombre = null)
            )
        }
    }

    fun onCorreoChange(valor: String) {
        _estado.update {
            it.copy(
                correo = valor,
                enviado = false,
                errores = it.errores.copy(correo = null)
            )
        }
    }

    fun onMensajeChange(valor: String) {
        _estado.update {
            it.copy(
                mensaje = valor,
                enviado = false,
                errores = it.errores.copy(mensaje = null)
            )
        }
    }

    // valida todos los campos, retorna true si no hay errores
    fun validarFormulario(): Boolean {
        val actual = _estado.value

        val errores = ContactoErrores(
            nombre = if (actual.nombre.isBlank()) "Campo obligatorio" else null,

            correo = if (actual.correo.isBlank()) "Campo obligatorio"
            else if (!Patterns.EMAIL_ADDRESS.matcher(actual.correo).matches())
                "Correo inválido"
            else null,

            mensaje = if (actual.mensaje.length < 10)
                "El mensaje debe tener al menos 10 caracteres"
            else null
        )

        val hayErrores = listOfNotNull(
            errores.nombre,
            errores.correo,
            errores.mensaje
        ).isNotEmpty()

        _estado.update {
            it.copy(
                errores = errores,
                enviado = !hayErrores
            )
        }

        return !hayErrores
    }

    // si el formulario es valido lo guarda en la bd
    fun enviar() {
        val actual = _estado.value

        if (validarFormulario()) {
            viewModelScope.launch {
                repository.guardar(
                    Contacto(
                        nombre = actual.nombre,
                        correo = actual.correo,
                        mensaje = actual.mensaje
                    )
                )

                // lmpia los campos despues de guardar
                _estado.update {
                    it.copy(
                        nombre = "",
                        correo = "",
                        mensaje = ""
                    )
                }
            }
        }
    }
}}
