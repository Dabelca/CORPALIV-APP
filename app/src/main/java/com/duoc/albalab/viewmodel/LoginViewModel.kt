package com.duoc.albalab.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.albalab.data.AppDatabase
import com.duoc.albalab.model.LoginErrores
import com.duoc.albalab.model.LoginUiState
import com.duoc.albalab.model.Socio
import com.duoc.albalab.repository.ContactoRepository
import com.duoc.albalab.repository.SocioRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// logica y validaciones del login
class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository =
        SocioRepository(AppDatabase.obtener(application).socioDao())

    private val contactoRepository =
        ContactoRepository(AppDatabase.obtener(application).contactoDao())

    private val _estado = MutableStateFlow(LoginUiState())
    val estado: StateFlow<LoginUiState> = _estado

    // cada cambio actualiza el campo y limpia su error
    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
    }

    fun onClaveChange(valor: String) {
        _estado.update { it.copy(clave = valor, errores = it.errores.copy(clave = null)) }
    }

    fun onNombreChange(valor: String) {
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }

    fun onConfirmarClaveChange(valor: String) {
        _estado.update {
            it.copy(confirmarClave = valor, errores = it.errores.copy(confirmarClave = null))
        }
    }

    // cambia entre el form de login y el de registro, con los campos vacios
    fun cambiarFormulario() {
        _estado.update { LoginUiState(registrando = !it.registrando) }
    }

    // valida los campos, retorna true si no hay errores
    fun validarFormulario(): Boolean {
        val actual = _estado.value

        val errores = LoginErrores(
            correo = if (!Patterns.EMAIL_ADDRESS.matcher(actual.correo).matches()) "Correo inválido" else null,
            clave = if (actual.clave.length < 6) "Debe tener al menos 6 caracteres" else null
        )

        val hayErrores = listOfNotNull(
            errores.correo,
            errores.clave
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }

        return !hayErrores
    }

    // si el formulario es valido busca el socio en la bd
    fun iniciarSesion() {
        if (validarFormulario()) {
            val actual = _estado.value
            viewModelScope.launch {
                val socio = repository.buscar(actual.correo, actual.clave)
                if (socio == null) {
                    _estado.update {
                        it.copy(errores = it.errores.copy(clave = "Correo o contraseña incorrectos"))
                    }
                } else {
                    _estado.update { it.copy(nombreSocio = socio.nombre) }
                    cargarSolicitudes()
                }
            }
        }
    }

    // valida los campos del registro, retorna true si no hay errores
    fun validarRegistro(): Boolean {
        val actual = _estado.value

        val errores = LoginErrores(
            nombre = if (actual.nombre.isBlank()) "El nombre es obligatorio" else null,
            correo = if (!actual.correo.contains("@")) "Correo inválido" else null,
            clave = if (actual.clave.length < 6) "Debe tener al menos 6 caracteres" else null,
            confirmarClave = if (actual.confirmarClave != actual.clave) "Las contraseñas no coinciden" else null
        )

        val hayErrores = listOfNotNull(
            errores.nombre,
            errores.correo,
            errores.clave,
            errores.confirmarClave
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }

        return !hayErrores
    }

    // si el registro es valido guarda el socio y vuelve al login con su correo
    fun registrar() {
        if (validarRegistro()) {
            val actual = _estado.value
            viewModelScope.launch {
                repository.insertar(
                    Socio(nombre = actual.nombre, correo = actual.correo, clave = actual.clave)
                )
                // muestra el mensaje de exito y espera antes de volver al login
                _estado.update { it.copy(registroExitoso = true) }
                delay(timeMillis = 2000)
                _estado.update { LoginUiState(correo = actual.correo) }
            }
        }
    }

    // busca en la bd las solicitudes enviadas con el correo del socio
    fun cargarSolicitudes() {
        val actual = _estado.value
        if (actual.nombreSocio != null) {
            viewModelScope.launch {
                val lista = contactoRepository.obtenerPorCorreo(actual.correo)
                _estado.update { it.copy(solicitudes = lista) }
            }
        }
    }

    // guarda la foto que viene de la galeria o la camara
    fun onFotoChange(uri: Uri) {
        _estado.update { it.copy(fotoPerfil = uri) }
    }

    // vuelve al formulario de login vacio
    fun cerrarSesion() {
        _estado.update { LoginUiState() }
    }
}
