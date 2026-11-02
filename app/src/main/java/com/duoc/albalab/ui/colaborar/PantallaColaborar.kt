package com.duoc.albalab.ui.colaborar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.duoc.albalab.viewmodel.ContactoViewModel

// Donaciones y formulario de contacto para "Quiero ayudar"
@Composable
fun PantallaColaborar(viewModel: ContactoViewModel = viewModel()) {
    val estado by viewModel.estado.collectAsState()
    // "personas", "empresas" o "" si no se ha elegido
    var tipoDonacion by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Donaciones",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = { tipoDonacion = if (tipoDonacion == "personas") "" else "personas"},
                modifier = Modifier.weight(1f)) {Text("Personas")
            }
            Button(onClick = { tipoDonacion = if (tipoDonacion == "empresas") "" else "empresas"},
                modifier = Modifier.weight(1f)) {Text("Empresas")
            }
        }

        if (tipoDonacion == "personas") {
            Text(
                "Transferencia bancaria directa\n" +
                    "Nombre: Corporación de Padres y Amigos por el Limitado Visual\n" +
                    "Rut: 73.100.200-2\n" +
                    "Banco de Chile, Cuenta corriente N° 17323468-02\n" +
                    "Email: administracionyfinanzas@corpaliv.cl"
            )
        }

        if (tipoDonacion == "empresas") {
            Text(
                "¿Cómo formalizar la donación?\n" +
                    "Como empresa debes conocer los “Mecanismos de donación” que tenemos disponibles en la siguiente sección. (Ley 21.015, Ley 19.855, Ley 21.440 y Ley 3.063)\n\n" +
                    "Luego debes contactar a Javiera Guzmán j.guzman@corpaliv.cl para solicitar más información.\n\n" +
                    "Si nos quieres apoyar, contáctanos:\n" +
                    "j.guzman@corpaliv.cl"
            )
        }

        Text(
            text = "Contáctanos",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        // Campo nombre
        OutlinedTextField(
            value = estado.nombre,
            onValueChange = viewModel::onNombreChange,
            label = { Text("Nombre") },
            isError = estado.errores.nombre != null,
            trailingIcon = { if (estado.errores.nombre != null) IconoError() },
            supportingText = {
                estado.errores.nombre?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo correo
        OutlinedTextField(
            value = estado.correo,
            onValueChange = viewModel::onCorreoChange,
            label = { Text("Correo electrónico") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = estado.errores.correo != null,
            trailingIcon = { if (estado.errores.correo != null) IconoError() },
            supportingText = {
                estado.errores.correo?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo mensaje
        OutlinedTextField(
            value = estado.mensaje,
            onValueChange = viewModel::onMensajeChange,
            label = { Text("Mensaje") },
            minLines = 3,
            isError = estado.errores.mensaje != null,
            trailingIcon = { if (estado.errores.mensaje != null) IconoError() },
            supportingText = {
                estado.errores.mensaje?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { viewModel.enviar() },
            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Enviar")
        }

        if (estado.enviado) {
            Text(
                text = "Gracias por tu interés! Te contactaremos pronto :)",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun IconoError() {
    Icon(
        imageVector = Icons.Filled.Error,
        contentDescription = "Error",
        tint = MaterialTheme.colorScheme.error
    )
}
