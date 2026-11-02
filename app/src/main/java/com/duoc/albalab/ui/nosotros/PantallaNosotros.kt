package com.duoc.albalab.ui.nosotros

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiPeople
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


private data class Seccion(val titulo: String, val texto: String, val icono: ImageVector)

private val mision = Seccion(
    "Misión",
    "Acompañar el desarrollo de niños, niñas, jóvenes y adultos con discapacidad múltiple durante toda su vida, con un modelo integral que respeta sus necesidades y diferencias.",
    Icons.Filled.Flag
)

private val vision = Seccion(
    "Visión",
    "Construir un país sin barreras para la discapacidad múltiple.",
    Icons.Filled.Visibility
)

private val valores = listOf(
    "Compromiso" to Icons.Filled.Handshake,
    "Respeto" to Icons.Filled.EmojiPeople,
    "Empatía" to Icons.Filled.Favorite,
    "Solidaridad" to Icons.Filled.VolunteerActivism
)

private val escuela = Seccion(
    "Escuela Especial Jan Van Dijk",
    "Creada en 2003, CORPALIV es su sostenedora. Es el eje del trabajo educativo de la corporación con niños, niñas y jóvenes con discapacidad múltiple.",
    Icons.Filled.School
)

private val programasLaborales = listOf(
    Seccion(
        "Red Sociolaboral Alba Lab",
        "Red de inclusión para personas desde los 18 que quieren trabajar, aprender y participar en la construcción de un país sin barreras.",
        Icons.Filled.Handshake
    ),
    Seccion(
        "Formación y preparación para el empleo",
        "Programas que entregan herramientas para que jóvenes y adultos se preparen para el mundo laboral.",
     Icons.Filled.Work
    ),
    Seccion(
        "Producción y venta de productos",
        "Los participantes elaboran productos con impacto social, como difusores y bolsas, que puedes ver en la Tienda.",
        Icons.Filled.Storefront

    )
)

private val historia = listOf(
    Seccion(
        "1993: Nace CORPALIV",
        "Un grupo de padres que no encontraba opciones educativas adaptadas para sus hijos con discapacidad múltiple crea la corporación.",
        Icons.Filled.Event
    ),
    Seccion(
        "2003: La Escuela Jan Van Dijk",
        "Gracias a un comodato se crea la escuela, que durante 20 años ha sido el eje principal de CORPALIV.",
        Icons.Filled.Event
    ),
    Seccion(
        "Hoy: Alba Lab",
        "Para acompañar a las personas durante toda su vida, nace la red sociolaboral Alba Lab, que integra a jóvenes y adultos.",
        Icons.Filled.Event
    )
)

@Composable
fun PantallaNosotros() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "¿Quiénes somos?",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        item {
            Text(
                text = "Somos una corporación sin fines de lucro con más de 30 años trabajando por las personas con discapacidad múltiple y su desarrollo.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        item { TituloSeccion("Misión y Visión") }
        item { TarjetaExpandible(mision) }
        item { TarjetaExpandible(vision) }

        item { TituloSeccion("Nuestros valores") }
        item { GrillaValores() }

        item { TituloSeccion("Educación") }
        item { TarjetaExpandible(escuela)}

        item { TituloSeccion("Inclusión laboral") }
        items (programasLaborales) { TarjetaExpandible(it) }

        item { TituloSeccion("Nuestra historia") }
        items (historia) { TarjetaExpandible(it) }
    }
}

@Composable
private fun TituloSeccion(texto: String){
    Text(
        text = texto,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(top = 8.dp)
    )
}

@Composable
private fun TarjetaExpandible(seccion: Seccion){
    var expandida by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expandida = !expandida }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .animateContentSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = seccion.icono,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = seccion.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expandida) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expandida) "Ocultar ${seccion.titulo}" else "Ver más sobre ${seccion.titulo}"
                )
            }
            if (expandida) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = seccion.texto,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun GrillaValores() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        valores.chunked(2).forEach { par ->
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                par.forEach { (nombre, icono) ->
                    Card(modifier = Modifier.weight(1f)) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = icono,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = nombre,
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    }
                }
            }
        }
    }
}


