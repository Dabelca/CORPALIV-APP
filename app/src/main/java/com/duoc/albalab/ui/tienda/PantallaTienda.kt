package com.duoc.albalab.ui.tienda

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.duoc.albalab.model.Producto
import com.duoc.albalab.viewmodel.TiendaViewModel
import java.text.NumberFormat
import java.util.Locale
import android.content.Context
import android.content.Intent
import android.speech.tts.TextToSpeech
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.setValue



//Catalogo de productos con filtro y favoritos
@Composable
fun PantallaTienda(viewModel: TiendaViewModel = viewModel()) {
    val estado by viewModel.estado.collectAsState()
    val context = LocalContext.current
    var lector by remember { mutableStateOf<TextToSpeech?>(null) }
    var leyendoId by remember { mutableStateOf<Int?>(null) }

    DisposableEffect(Unit) {
        var motor: TextToSpeech? = null
        motor = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                motor?.language = Locale.forLanguageTag("es-CL")
            }
        }
        lector = motor
        onDispose {
            motor?.stop()
            motor?.shutdown()
        }

    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Productos hechos por jovenes y adultos de Alba Lab",
                style = MaterialTheme.typography.titleMedium
            )
        }
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(estado.tipos) { tipo ->
                    FilterChip(
                        selected = estado.tipoSeleccionado == tipo,
                        onClick = { viewModel.onTipoSeleccionado(tipo) },
                        label = { Text(tipo) }
                    )
                }
            }
        }
        items(estado.productos, key = { it.id }) { producto ->
            var visible by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) { visible = true }
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween(400)) +
                        slideInVertically(animationSpec = tween(400)) { it / 3 },
                modifier = Modifier.animateItem()
            ) {
                TarjetaProducto(
                    producto = producto,
                    onEscuchar = {
                        if (leyendoId == it.id && lector?.isSpeaking == true) {
                            lector?.stop()
                            leyendoId = null
                        } else {
                            leerProducto(lector, it)
                            leyendoId = it.id
                        }
                    }
                )
            }
        }
    }
}
@Composable
private fun TarjetaProducto(producto: Producto, onEscuchar: (Producto) -> Unit){
    val context = LocalContext.current
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = producto.imagen),
                contentDescription = producto.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Column(modifier = Modifier.weight(1f)){
               Text(
                   text = producto.tipo,
                   style = MaterialTheme.typography.labelMedium,
                   color = MaterialTheme.colorScheme.secondary
               )
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = producto.descripcion,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = formatoPrecio(producto.precio),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Column{
                IconButton(onClick = {onEscuchar(producto)}) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = "Escuchar ${producto.nombre}",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = { compartirProducto(context, producto) }) {
                 Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = "Compartir ${producto.nombre}",
                    tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}


private fun formatoPrecio(precio: Int): String =
    "$" + NumberFormat.getNumberInstance(Locale.forLanguageTag("es-CL")).format(precio)


private fun compartirProducto(context: Context, producto: Producto) {
    val texto = "Mira este producto de Alba Lab: ${producto.nombre} a ${formatoPrecio(producto.precio)}."
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, texto)
    }
    context.startActivity(Intent.createChooser(intent,"Compartir producto"))

}

private fun leerProducto(lector: TextToSpeech?, producto: Producto) {
    val texto = "${producto.nombre}. ${producto.descripcion}. Precio: ${producto.precio} pesos."
    lector?.speak(texto, TextToSpeech.QUEUE_FLUSH, null, "producto_${producto.id}")
}