package com.duoc.albalab

import com.duoc.albalab.viewmodel.TiendaViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk

class TiendaViewModelTest : StringSpec({

    "al iniciar muestra todos los productos sin filtro" {
        val viewModel = TiendaViewModel(mockk(relaxed = true))
        viewModel.estado.value.productos.size shouldBe 8
        viewModel.estado.value.tipoSeleccionado shouldBe null
    }

    "los tipos no se repiten" {
        val viewModel = TiendaViewModel(mockk(relaxed = true))
        val tipos = viewModel.estado.value.tipos
        tipos.size shouldBe tipos.distinct().size
    }

    "onTipoSeleccionado() filtra los productos por tipo" {
        val viewModel = TiendaViewModel(mockk(relaxed = true))
        viewModel.onTipoSeleccionado("Bienestar")
        viewModel.estado.value.tipoSeleccionado shouldBe "Bienestar"
        viewModel.estado.value.productos.all { it.tipo == "Bienestar" } shouldBe true
    }

    "tocar el mismo tipo otra vez quita el filtro" {
        val viewModel = TiendaViewModel(mockk(relaxed = true))
        viewModel.onTipoSeleccionado("Bienestar")
        viewModel.onTipoSeleccionado("Bienestar")
        viewModel.estado.value.tipoSeleccionado shouldBe null
        viewModel.estado.value.productos.size shouldBe 8
    }
})