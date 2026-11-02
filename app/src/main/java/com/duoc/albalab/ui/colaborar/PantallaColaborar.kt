package com.duoc.albalab.ui.colaborar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.duoc.albalab.viewmodel.ContactoViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.ui.window.PopupProperties
import androidx.compose.foundation.clickable
import androidx.compose.material3.DropdownMenu
import androidx.compose.foundation.layout.Box

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

        // el boton seleccionado cambia de color con animacion
        val colorPersonas by animateColorAsState(
            targetValue = if (tipoDonacion == "personas") MaterialTheme.colorScheme.primary else Color(0xFFB0BEC5),
            animationSpec = tween(durationMillis = 500), label = ""
        )
        val colorEmpresas by animateColorAsState(
            targetValue = if (tipoDonacion == "empresas") MaterialTheme.colorScheme.primary else Color(0xFFB0BEC5),
            animationSpec = tween(durationMillis = 500), label = ""
        )

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = { tipoDonacion = if (tipoDonacion == "personas") "" else "personas" },
                colors = ButtonDefaults.buttonColors(containerColor = colorPersonas),
                modifier = Modifier.weight(1f)) {
                Text("Personas")
            }
            Button(onClick = { tipoDonacion = if (tipoDonacion == "empresas") "" else "empresas" },
                colors = ButtonDefaults.buttonColors(containerColor = colorEmpresas),
                modifier = Modifier.weight(1f)) {
                Text("Empresas")
            }
        }

        if (tipoDonacion == "personas") {
            Text(
                "Transferencia bancaria directa\n" +
                        "Nombre: Corporación de Padres y Amigos por el Limitado Visual\n" +
                        "Rut: 12.345.678-9\n" +
                        "Banco de Chile, Cuenta corriente N° 00000000-00\n" +
                        "Email: administracionyfinanzas@fundacion.cl"
            )
        }

        if (tipoDonacion == "empresas") {
            Text(
                "¿Cómo formalizar la donación?\n" +
                        "Como empresa debes conocer los “Mecanismos de donación” que tenemos disponibles en la siguiente sección. (Ley 21.015, Ley 19.855, Ley 21.440 y Ley 3.063)\n\n" +
                        "Luego debes contactar a Jane Doe j.doe@fundacion.cl para solicitar más información.\n\n" +
                        "Si nos quieres apoyar, contáctanos:\n" +
                        "j.doe@fundacion.cl"
            )
        }

        Text(
            text = "Contáctanos",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        // tipo de colaborador
        Text(
            text = "Tipo de colaborador",
            style = MaterialTheme.typography.titleMedium
        )

        Row (verticalAlignment = Alignment.CenterVertically
            ) {
            RadioButton(
                selected = estado.tipoColaborador == "Persona",
                onClick = { viewModel.onTipoColaboradorChange("Persona") }
            )
            Text("Persona")
        }
        Row(verticalAlignment = Alignment.CenterVertically
            ){
            RadioButton(
                selected = estado.tipoColaborador == "Empresa",
                onClick = { viewModel.onTipoColaboradorChange("Empresa") }
            )
            Text("Empresa")
        }


        // tipo de colaboracion
        var expandido by remember { mutableStateOf(false) }

        // la flecha cambia de color al abrir la lista
        val colorFlecha by animateColorAsState(
            targetValue = if (expandido) MaterialTheme.colorScheme.primary else Color(0xFFB0BEC5),
            animationSpec = tween(durationMillis = 500), label = ""
        )

        val opcionesColaboracion = listOf(
            "Donación económica",
            "Donación de productos o materiales",
            "Voluntariado",
            "Alianza con empresas",
            "Otra forma de colaborar"
        )

        Text(text = "¿Cómo desea colaborar?")

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = estado.formaColaboracion,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Seleccione una opción") },
                trailingIcon = {
                    Text(if (expandido) "▲" else "▼", color = colorFlecha)
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Capa transparente para detectar el clic
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable {
                        expandido = !expandido
                    }
            )

            DropdownMenu(
                expanded = expandido,
                onDismissRequest = { expandido = false },
                properties = PopupProperties(focusable = true)
            ) {
                opcionesColaboracion.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            viewModel.onFormaColaboracionChange(opcion)
                            expandido = false
                        }
                    )
                }
            }
        }

        // Campo nombre
        OutlinedTextField(
            value = estado.nombre,
            onValueChange = viewModel::onNombreChange,
            label = { Text("Nombre") },
            isError = estado.errores.nombre != null,
            trailingIcon = { if (estado.errores.nombre != null) IconoError() },
            // el error aparece con aniamcion
            supportingText = {
                AnimatedVisibility(visible = estado.errores.nombre != null) {
                    Text(estado.errores.nombre ?: "", color = MaterialTheme.colorScheme.error)
                }
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
                AnimatedVisibility(visible = estado.errores.correo != null) {
                    Text(estado.errores.correo ?: "", color = MaterialTheme.colorScheme.error)
                }
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
                AnimatedVisibility(visible = estado.errores.mensaje != null) {
                    Text(estado.errores.mensaje ?: "", color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // mientars se guarda se muestra el loader en vez del boton
        if (estado.guardando) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = { viewModel.enviar() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enviar")
            }
        }

        if (estado.enviado && !estado.guardando) {
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
