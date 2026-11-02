package com.duoc.albalab.ui.components

import android.net.Uri
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

// muestra la foto en circulo, si no hay foto muestra el icono de persona
@Composable
fun ImagenInteligente(uri: Uri?) {
    if (uri != null) {
        AsyncImage(
            model = uri,
            contentDescription = "Foto de perfil",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
        )
    } else {
        Icon(
            imageVector = Icons.Filled.Person,
            contentDescription = "Sin foto de perfil",
            modifier = Modifier.size(56.dp)
        )
    }
}
