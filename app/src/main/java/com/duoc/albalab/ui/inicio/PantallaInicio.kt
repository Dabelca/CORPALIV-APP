package com.duoc.albalab.ui.inicio

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duoc.albalab.R
import com.duoc.albalab.ui.theme.AlbaLabTheme

//Pagina principal con logo, foto y boton "Quiero ayudar"
@Composable
fun PantallaInicio(onQuieroAyudar: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Logo de CORPALIV
        Image(
            painter = painterResource(id = R.drawable.logo_corpaliv),
            contentDescription = "Logo de CORPALIV",
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            contentScale = ContentScale.Fit
        )

        // Foto de Alba Lab
        Image(
            painter = painterResource(id = R.drawable.foto_inicio),
            contentDescription = "Jóvenes y adultos de Alba Lab en su práctica laboral",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Fit
        )

        // Titulo con el color principal de la paleta
        Text(
            text = "Alba Lab",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Apoyamos a personas con discapacidad múltiple para que estudien, trabajen y participen en su comunidad.",
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )

        // Boton con el naranjo de la paleta
        Button(
            onClick = onQuieroAyudar,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Quiero ayudar", fontSize = 20.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaInicioPreview() {
    AlbaLabTheme {
        PantallaInicio(onQuieroAyudar = {})
    }
}
