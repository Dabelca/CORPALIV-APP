package com.duoc.albalab

import com.duoc.albalab.data.remote.IndicadoresApi
import com.duoc.albalab.model.Indicador
import com.duoc.albalab.model.IndicadoresResponse
import com.duoc.albalab.repository.IndicadoresRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class IndicadoresRepositoryTest : StringSpec({

    val respuesta = IndicadoresResponse(
        uf = Indicador("uf", "unidad de fomento", 39000.0),
        utm = Indicador("utm", "Unidad tributaria mensual", 68000.0)
    )
    "obtenerIndicadores() debe retornar la UF y la UTM de la API externa" {
        val api = mockk<IndicadoresApi>()
        coEvery { api.obtenerIndicadores() } returns respuesta
        val repo = IndicadoresRepository(api)
        runTest {
            val resultado = repo.obtenerIndicadores()
            resultado.uf.valor shouldBe 39000.0
            resultado.utm.valor shouldBe 68000.0
        }
    }
})
