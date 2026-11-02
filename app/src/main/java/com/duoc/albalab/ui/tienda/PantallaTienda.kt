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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.duoc.albalab.model.Producto
import com.duoc.albalab.viewmodel.TiendaViewModel
import java.text.NumberFormat
import java.util.Locale


//Catalogo de productos con filtro y favoritos
@Composable
fun PantallaTienda(viewModel: TiendaViewModel = viewModel()) {
    val estado by viewModel.estado.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item{
            Text(
            text = "Productos hechos por jovenes y adultos de Alba Lab",
            style = MaterialTheme.typography.titleMedium
            )
        }
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)){
                items(estado.tipos) {tipo ->
                    FilterChip(
                        selected = estado.tipoSeleccionado == tipo,
                        onClick = {viewModel.onTipoSeleccionado(tipo)},
                        label = { Text(tipo) }
                    )
                }
            }
        }
        items(estado.productos, key = {it.id}) {producto ->
            TarjetaProducto(producto)
        }

    }

}

@Composable
private fun TarjetaProducto(producto: Producto){
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
        }
    }
}


private fun formatoPrecio(precio: Int): String =
    "$" + NumberFormat.getNumberInstance(Locale.forLanguageTag("es-CL")).format(precio)