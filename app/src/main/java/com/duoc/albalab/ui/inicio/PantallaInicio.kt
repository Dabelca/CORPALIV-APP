package com.duoc.albalab.ui.inicio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duoc.albalab.ui.theme.AlbaLabTheme

//Pagina pricnipal
@Composable
fun PantallaInicio(onQuieroAyudar: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Alba Lab",
            fontSize = 32.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Apoyamos a personas con discapacidad múltiple para que estudien, trabajen y participen en su comunidad.",
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onQuieroAyudar,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
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
