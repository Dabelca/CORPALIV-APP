package com.duoc.albalab.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.duoc.albalab.model.ContactoErrores
import com.duoc.albalab.model.ContactoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

// Logica y validaciones del formulario de contacto
class ContactoViewModel : ViewModel() {

    private val _estado = MutableStateFlow(ContactoUiState())
    val estado: StateFlow<ContactoUiState> = _estado

    // Cada cambio actualiza el campo y limpia su error
    fun onNombreChange(valor: String) {
        _estado.update { it.copy(nombre = valor, enviado = false, errores = it.errores.copy(nombre = null)) }
    }

    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correo = valor, enviado = false, errores = it.errores.copy(correo = null)) }
    }

    fun onMensajeChange(valor: String) {
        _estado.update { it.copy(mensaje = valor, enviado = false, errores = it.errores.copy(mensaje = null)) }
    }

    // Valida todos los campos, retorna true si no hay errores
    fun validarFormulario(): Boolean {
        val actual = _estado.value
        val errores = ContactoErrores(
            nombre = if (actual.nombre.isBlank()) "Campo obligatorio" else null,
            correo = if (actual.correo.isBlank()) "Campo obligatorio"
                else if (!Patterns.EMAIL_ADDRESS.matcher(actual.correo).matches()) "Correo inválido"
                else null,
            mensaje = if (actual.mensaje.length < 10) "El mensaje debe tener al menos 10 caracteres" else null
        )

        val hayErrores = listOfNotNull(errores.nombre, errores.correo, errores.mensaje).isNotEmpty()

        _estado.update {
            it.copy(
                errores = errores,
                enviado = !hayErrores,
                nombre = if (!hayErrores) "" else it.nombre,
                correo = if (!hayErrores) "" else it.correo,
                mensaje = if (!hayErrores) "" else it.mensaje
            )
        }

        return !hayErrores
    }
}
