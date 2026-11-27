package com.duoc.albalab

import com.duoc.albalab.data.remote.ApiService
import com.duoc.albalab.model.Socio
import com.duoc.albalab.repository.SocioRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class SocioRepositoryTest : StringSpec({

    val socio = Socio(id = 1, nombre = "Socio Prueba", correo = "socio@prueba.cl", clave = "123456")

    "login() debe retornar el socio del microservicio" {
        val mockApi = mockk<ApiService>()
        coEvery { mockApi.login(Socio(correo = "socio@prueba.cl", clave = "123456")) } returns socio
        val repo = SocioRepository(mockApi)
        runTest {
            repo.login("socio@prueba.cl", "123456") shouldBe socio
        }
    }

    "registrar() debe retornar el socio creado" {
        val mockApi = mockk<ApiService>()
        coEvery { mockApi.registrar(socio) } returns socio
        val repo = SocioRepository(mockApi)
        runTest {
            repo.registrar(socio) shouldBe socio
        }
    }

    "actualizar() debe retornar el socio con los cambios" {
        val editado = socio.copy(nombre = "Nombre Nuevo")
        val mockApi = mockk<ApiService>()
        coEvery { mockApi.actualizar(1, editado) } returns editado
        val repo = SocioRepository(mockApi)
        runTest {
            repo.actualizar(1, editado) shouldBe editado
        }
    }

    "eliminar() debe llamar al endpoint de eliminar" {
        val mockApi = mockk<ApiService>()
        coEvery { mockApi.eliminar(1) } returns Unit
        val repo = SocioRepository(mockApi)
        runTest {
            repo.eliminar(1)
        }
        coVerify { mockApi.eliminar(1) }
    }
})
