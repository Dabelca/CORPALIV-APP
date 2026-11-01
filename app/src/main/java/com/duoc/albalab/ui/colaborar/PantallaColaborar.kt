package com.duoc.albalab.ui.colaborar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.duoc.albalab.viewmodel.ContactoViewModel

// Formulario de contacto para "Quiero ayudar"
@Composable
fun PantallaColaborar(viewModel: ContactoViewModel = viewModel()) {
    val estado by viewModel.estado.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
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
            onClick = { viewModel.validarFormulario() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar")
        }

        if (estado.enviado) {
            Text(
                text = "¡Gracias! Te contactaremos pronto.",
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
