package com.duoc.albalab

import com.duoc.albalab.model.Indicador
import com.duoc.albalab.model.IndicadoresResponse
import com.duoc.albalab.model.Solicitud
import com.duoc.albalab.repository.ContactoRepository
import com.duoc.albalab.repository.IndicadoresRepository
import com.duoc.albalab.viewmodel.ContactoViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class ContactoViewModelTest : StringSpec({

    val indicadores = IndicadoresResponse(
        uf = Indicador("uf", "Unidad de fomento", 39000.0),
        utm = Indicador("utm", "Unidad tributaria mensual", 68000.0)
    )
    val solicitud = Solicitud(
        id = 1,
        nombre = "Ana",
        correo = "ana@prueba.cl",
        tipoColaborador = "Persona",
        formaColaboracion = "Voluntariado",
        mensaje = "Quiero ayudar en Alba Lab"
    )
    lateinit var repo: ContactoRepository
    lateinit var indicadoresRepo: IndicadoresRepository
    lateinit var viewModel: ContactoViewModel

    beforeTest {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        repo = mockk()
        indicadoresRepo = mockk()
        coEvery { indicadoresRepo.obtenerIndicadores() } returns indicadores
        viewModel = ContactoViewModel(mockk(relaxed = true), repo, indicadoresRepo)
    }

    afterTest {
        Dispatchers.resetMain()
    }

    fun llenarFormulario() {
        viewModel.onNombreChange("Ana")
        viewModel.onCorreoChange("ana@prueba.cl")
        viewModel.onMensajeChange("Quiero ayudar en Alba Lab")
        viewModel.onTipoColaboradorChange("Persona")
        viewModel.onFormaColaboracionChange("Voluntariado")
    }

    "al iniciar carga la UF y la UTM de la API externa" {
        viewModel.estado.value.indicadores shouldBe indicadores
        viewModel.estado.value.errorIndicadores shouldBe false
    }

    "cargarIndicadores() con error marca errorIndicadores" {
        coEvery { indicadoresRepo.obtenerIndicadores() } throws Exception("sin internet")
        viewModel.cargarIndicadores()
        viewModel.estado.value.errorIndicadores shouldBe true
    }

    "validarFormulario() detecta todos los campos invalidos" {
        viewModel.onCorreoChange("correo")
        viewModel.onMensajeChange("corto")
        viewModel.validarFormulario() shouldBe false
        val errores = viewModel.estado.value.errores
        errores.nombre shouldBe "Campo obligatorio"
        errores.correo shouldBe "Correo inválido"
        errores.mensaje shouldBe "El mensaje debe tener al menos 10 caracteres"
    }

    "enviar() con datos validos envia la solicitud y limpia el formulario" {
        coEvery { repo.crear(any()) } returns solicitud
        llenarFormulario()
        viewModel.enviar()
        coVerify { repo.crear(any()) }
        viewModel.estado.value.nombre shouldBe ""
        viewModel.estado.value.enviado shouldBe true
        viewModel.estado.value.errorEnvio shouldBe false
    }

    "enviar() con error del microservicio muestra el mensaje de error" {
        coEvery { repo.crear(any()) } throws Exception("timeout")
        llenarFormulario()
        viewModel.enviar()
        viewModel.estado.value.errorEnvio shouldBe true
        viewModel.estado.value.guardando shouldBe false
    }

    "enviar() con datos invalidos no llama al microservicio" {
        viewModel.enviar()
        coVerify(exactly = 0) { repo.crear(any()) }
    }

    "onTipoColaboradorChange() y onFormaColaboracionChange() guardan la seleccion" {
        viewModel.onTipoColaboradorChange("Empresa")
        viewModel.onFormaColaboracionChange("Alianza con empresas")
        viewModel.estado.value.tipoColaborador shouldBe "Empresa"
        viewModel.estado.value.formaColaboracion shouldBe "Alianza con empresas"
    }
})