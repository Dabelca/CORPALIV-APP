package com.duoc.albalab

import com.duoc.albalab.data.remote.SolicitudApi
import com.duoc.albalab.model.Solicitud
import com.duoc.albalab.repository.ContactoRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class ContactoRepositoryTest : StringSpec({

    val solicitud = Solicitud(
        id = 1,
        nombre = "Ana",
        correo = "ana@prueba.cl",
        tipoColaborador = "Persona",
        formaColaboracion = "Voluntariado",
        mensaje = "Quiero ayudar en Alba Lab",
        estado = "Enviada"
    )

    "crear() debe retornar la solicitud creada por el microservicio" {
        val api = mockk<SolicitudApi>()
        coEvery { api.crear(solicitud) } returns solicitud
        val repo = ContactoRepository(api)
        runTest {
            repo.crear(solicitud) shouldBe solicitud
        }
    }

    "obtenerPorCorreo() debe retornar las solicitudes de ese correo" {
        val api = mockk<SolicitudApi>()
        coEvery { api.listarPorCorreo("ana@prueba.cl") } returns listOf(solicitud)
        val repo = ContactoRepository(api)
        runTest {
            repo.obtenerPorCorreo("ana@prueba.cl") shouldBe listOf(solicitud)
        }
    }

    "actualizar() debe retornar la solicitud con los cambios" {
        val editada = solicitud.copy(mensaje = "Ahora quiero hacer una alianza")
        val api = mockk<SolicitudApi>()
        coEvery { api.actualizar(1, editada) } returns editada
        val repo = ContactoRepository(api)
        runTest {
            repo.actualizar(1, editada) shouldBe editada
        }
    }

    "eliminar() debe llamar al microservicio con el id" {
        val api = mockk<SolicitudApi>()
        coEvery { api.eliminar(1) } returns Unit
        val repo = ContactoRepository(api)
        runTest {
            repo.eliminar(1)
        }
        coVerify { api.eliminar(1) }
    }
})