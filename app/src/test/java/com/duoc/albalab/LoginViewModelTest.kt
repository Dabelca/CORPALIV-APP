package com.duoc.albalab

import android.net.Uri
import com.duoc.albalab.model.Solicitud
import com.duoc.albalab.model.Socio
import com.duoc.albalab.repository.ContactoRepository
import com.duoc.albalab.repository.SocioRepository
import com.duoc.albalab.viewmodel.LoginViewModel
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
class LoginViewModelTest : StringSpec({

    val socio = Socio(id = 1, nombre = "Socio Prueba", correo = "socio@prueba.cl", clave = "123456")
    lateinit var repo: SocioRepository
    lateinit var contactoRepo: ContactoRepository
    lateinit var viewModel: LoginViewModel

    // antes de cada test se crea un viewModel nuevo con mocks
    beforeTest {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        repo = mockk()
        contactoRepo = mockk()
        coEvery { contactoRepo.obtenerPorCorreo(any()) } returns emptyList()
        viewModel = LoginViewModel(mockk(relaxed = true), repo, contactoRepo)
    }

    afterTest {
        Dispatchers.resetMain()
    }

    fun iniciarSesion() {
        coEvery { repo.login("socio@prueba.cl", "123456") } returns socio
        viewModel.onCorreoChange("socio@prueba.cl")
        viewModel.onClaveChange("123456")
        viewModel.iniciarSesion()
    }

    "iniciarSesion() con datos correctos guarda el socio" {
        iniciarSesion()
        viewModel.estado.value.nombreSocio shouldBe "Socio Prueba"
        viewModel.estado.value.idSocio shouldBe 1
    }

    "iniciarSesion() con clave incorrecta muestra un error" {
        coEvery { repo.login(any(), any()) } throws Exception("401")
        viewModel.onCorreoChange("socio@prueba.cl")
        viewModel.onClaveChange("clave_mala")
        viewModel.iniciarSesion()
        viewModel.estado.value.nombreSocio shouldBe null
        viewModel.estado.value.errores.clave shouldBe "Correo o contraseña incorrectos"
    }

    "iniciarSesion() con datos invalidos no llama al microservicio" {
        viewModel.onCorreoChange("correo")
        viewModel.onClaveChange("123")
        viewModel.iniciarSesion()
        viewModel.estado.value.errores.correo shouldBe "Correo inválido"
        viewModel.estado.value.errores.clave shouldBe "Debe tener al menos 6 caracteres"
        coVerify(exactly = 0) { repo.login(any(), any()) }
    }

    "iniciarSesion() carga las solicitudes del socio" {
        val solicitud = Solicitud(nombre = "Socio Prueba", correo = "socio@prueba.cl", tipoColaborador = "Persona", formaColaboracion = "Voluntariado", mensaje = "Hola")
        coEvery { contactoRepo.obtenerPorCorreo("socio@prueba.cl") } returns listOf(solicitud)
        iniciarSesion()
        viewModel.estado.value.solicitudes shouldBe listOf(solicitud)
    }

    "cambiarFormulario() muestra el registro con los campos vacios" {
        viewModel.onCorreoChange("socio@prueba.cl")
        viewModel.cambiarFormulario()
        viewModel.estado.value.registrando shouldBe true
        viewModel.estado.value.correo shouldBe ""
    }

    "validarRegistro() detecta todos los campos invalidos" {
        viewModel.onNombreChange("")
        viewModel.onCorreoChange("correo")
        viewModel.onClaveChange("123")
        viewModel.onConfirmarClaveChange("456")
        viewModel.validarRegistro() shouldBe false
        val errores = viewModel.estado.value.errores
        errores.nombre shouldBe "El nombre es obligatorio"
        errores.correo shouldBe "Correo inválido"
        errores.clave shouldBe "Debe tener al menos 6 caracteres"
        errores.confirmarClave shouldBe "Las contraseñas no coinciden"
    }

    "registrar() con datos validos muestra el mensaje de exito" {
        coEvery { repo.registrar(any()) } returns socio
        viewModel.onNombreChange("Socio Prueba")
        viewModel.onCorreoChange("socio@prueba.cl")
        viewModel.onClaveChange("123456")
        viewModel.onConfirmarClaveChange("123456")
        viewModel.registrar()
        viewModel.estado.value.registroExitoso shouldBe true
    }

    "registrar() con error muestra un mensaje" {
        coEvery { repo.registrar(any()) } throws Exception("error")
        viewModel.onNombreChange("Socio Prueba")
        viewModel.onCorreoChange("socio@prueba.cl")
        viewModel.onClaveChange("123456")
        viewModel.onConfirmarClaveChange("123456")
        viewModel.registrar()
        viewModel.estado.value.errores.correo shouldBe "No se pudo registrar"
    }

    "editarPerfil() y guardarPerfil() actualizan el nombre" {
        iniciarSesion()
        viewModel.editarPerfil()
        viewModel.estado.value.editando shouldBe true
        coEvery { repo.actualizar(1, any()) } returns socio.copy(nombre = "Nombre Nuevo")
        viewModel.onNombreChange("Nombre Nuevo")
        viewModel.guardarPerfil()
        viewModel.estado.value.nombreSocio shouldBe "Nombre Nuevo"
        viewModel.estado.value.editando shouldBe false
    }

    "guardarPerfil() con error muestra un mensaje" {
        iniciarSesion()
        viewModel.editarPerfil()
        coEvery { repo.actualizar(any(), any()) } throws Exception("error")
        viewModel.guardarPerfil()
        viewModel.estado.value.errores.correo shouldBe "No se pudo guardar"
    }

    "cancelarEdicion() vuelve al perfil" {
        iniciarSesion()
        viewModel.editarPerfil()
        viewModel.cancelarEdicion()
        viewModel.estado.value.editando shouldBe false
    }

    "eliminarCuenta() borra la cuenta y vuelve al login" {
        iniciarSesion()
        coEvery { repo.eliminar(1) } returns Unit
        viewModel.eliminarCuenta()
        viewModel.estado.value.nombreSocio shouldBe null
        coVerify { repo.eliminar(1) }
    }

    "eliminarCuenta() con error mantiene la sesion" {
        iniciarSesion()
        coEvery { repo.eliminar(any()) } throws Exception("error")
        viewModel.eliminarCuenta()
        viewModel.estado.value.nombreSocio shouldBe "Socio Prueba"
    }

    "onFotoChange() guarda la foto" {
        val uri = mockk<Uri>()
        viewModel.onFotoChange(uri)
        viewModel.estado.value.fotoPerfil shouldBe uri
    }

    "cerrarSesion() vuelve al login vacio" {
        iniciarSesion()
        viewModel.cerrarSesion()
        viewModel.estado.value.nombreSocio shouldBe null
        viewModel.estado.value.correo shouldBe ""
    }
})
