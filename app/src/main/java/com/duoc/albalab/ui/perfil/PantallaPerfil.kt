package com.duoc.albalab.ui.perfil

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.duoc.albalab.model.Contacto
import com.duoc.albalab.viewmodel.LoginViewModel

// login y registro de socios
@Composable
fun PantallaPerfil(viewModel: LoginViewModel = viewModel()) {
    val estado by viewModel.estado.collectAsState()

    // cada vez que se abre la pantalla recarga las solicitudes
    LaunchedEffect(Unit) {
        viewModel.cargarSolicitudes()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // si ya inicio sesion muestra el saludo y sus solicitudes
        if (estado.nombreSocio != null) {
            Text(
                text = "Hola, ${estado.nombreSocio}",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Button(
                onClick = { viewModel.cerrarSesion() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar sesión")
            }

            Text(
                text = "Mis solicitudes",
                style = MaterialTheme.typography.titleMedium
            )

            if (estado.solicitudes.isEmpty()) {
                Text("No has enviado solicitudes")
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(estado.solicitudes, key = { it.id }) { solicitud ->
                        TarjetaSolicitud(solicitud)
                    }
                }
            }
        } else if (estado.registrando) {
            Text(
                text = "Crear cuenta",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            // campo nombre
            OutlinedTextField(
                value = estado.nombre,
                onValueChange = viewModel::onNombreChange,
                label = { Text("Nombre") },
                isError = estado.errores.nombre != null,
                supportingText = {
                    estado.errores.nombre?.let { MensajeError(it) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // campo correo
            OutlinedTextField(
                value = estado.correo,
                onValueChange = viewModel::onCorreoChange,
                label = { Text("Correo electrónico") },
                isError = estado.errores.correo != null,
                supportingText = {
                    estado.errores.correo?.let { MensajeError(it) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // campo clave
            OutlinedTextField(
                value = estado.clave,
                onValueChange = viewModel::onClaveChange,
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                isError = estado.errores.clave != null,
                supportingText = {
                    estado.errores.clave?.let { MensajeError(it) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // campo confirmar clave
            OutlinedTextField(
                value = estado.confirmarClave,
                onValueChange = viewModel::onConfirmarClaveChange,
                label = { Text("Confirmar contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                isError = estado.errores.confirmarClave != null,
                supportingText = {
                    estado.errores.confirmarClave?.let { MensajeError(it) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { viewModel.registrar() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrarme")
            }

            // mensaje animado cuando se guarda la cuenta
            AnimatedVisibility(visible = estado.registroExitoso) {
                Text(
                    text = "¡Te has registrado con éxito!",
                    color = Color(0xFF4CAF50),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            EnlacePequeno("¿Ya tienes cuenta? Inicia sesión") { viewModel.cambiarFormulario() }
        } else {
            Text(
                text = "Iniciar sesión",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            // Campo correo
            OutlinedTextField(
                value = estado.correo,
                onValueChange = viewModel::onCorreoChange,
                label = { Text("Correo electrónico") },
                isError = estado.errores.correo != null,
                supportingText = {
                    estado.errores.correo?.let { MensajeError(it) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Campo clave
            OutlinedTextField(
                value = estado.clave,
                onValueChange = viewModel::onClaveChange,
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                isError = estado.errores.clave != null,
                supportingText = {
                    estado.errores.clave?.let { MensajeError(it) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { viewModel.iniciarSesion() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ingresar")
            }

            EnlacePequeno("¿No tienes cuenta? Regístrate") { viewModel.cambiarFormulario() }
        }
    }
}

// tarjeta con los datos de una solicitud enviada
@Composable
private fun TarjetaSolicitud(solicitud: Contacto) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = solicitud.tipoColaborador,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            if (solicitud.formaColaboracion.isNotBlank()) {
                Text(
                    text = solicitud.formaColaboracion,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(text = solicitud.mensaje)
        }
    }
}

// texto chico centrado que se puede tocar, para cambiar entre login y registro
@Composable
private fun EnlacePequeno(texto: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = texto,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { onClick() }
        )
    }
}

// icono y texto de error debajo del campo
@Composable
private fun MensajeError(mensaje: String) {
    Row {
        Icon(
            imageVector = Icons.Filled.Info,
            contentDescription = "Error",
            tint = MaterialTheme.colorScheme.error
        )
        Spacer(Modifier.width(8.dp))
        Text(text = mensaje, color = MaterialTheme.colorScheme.error)
    }
}
