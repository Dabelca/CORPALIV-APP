package com.duoc.albalab.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.duoc.albalab.model.Producto
import com.duoc.albalab.model.TiendaUiState
import com.duoc.albalab.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class TiendaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ProductoRepository()

    private val _estado = MutableStateFlow(
        TiendaUiState(
            productos = repository.obtenerProductos(),
            tipos = repository.obtenerTipos()
        )
    )

    val estado: StateFlow<TiendaUiState> = _estado

    fun onTipoSeleccionado(tipo: String) {
        _estado.update {
            val nuevoTipo = if (it.tipoSeleccionado == tipo) null else tipo
            val todos = repository.obtenerProductos()
            it.copy(
                tipoSeleccionado = nuevoTipo,
                productos = if (nuevoTipo == null) todos else todos.filter { p -> p.tipo == nuevoTipo}
            )
        }
    }
}