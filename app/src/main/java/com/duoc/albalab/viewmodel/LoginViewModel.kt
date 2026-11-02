package com.duoc.albalab.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.albalab.data.AppDatabase
import com.duoc.albalab.model.LoginErrores
import com.duoc.albalab.model.LoginUiState
import com.duoc.albalab.repository.SocioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// logica y validaciones del login
class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository =
        SocioRepository(AppDatabase.obtener(application).socioDao())

    private val _estado = MutableStateFlow(LoginUiState())
    val estado: StateFlow<LoginUiState> = _estado

    // cada cambio actualiza el campo y limpia su error
    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
    }

    fun onClaveChange(valor: String) {
        _estado.update { it.copy(clave = valor, errores = it.errores.copy(clave = null)) }
    }

    // valida los campos, retorna true si no hay errores
    fun validarFormulario(): Boolean {
        val actual = _estado.value

        val errores = LoginErrores(
            correo = if (!actual.correo.contains("@")) "Correo inválido" else null,
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
                }
            }
        }
    }
}
