package com.duoc.albalab.ui.theme

import android.app.Activity
import androidx.compose.ui.graphics.Color
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = MoradoNoche,
    onPrimary = MoradoNocheFondo,
    primaryContainer = MoradoNocheContenedor,
    onPrimaryContainer = MoradoClaro,
    secondary = NaranjoNoche,
    onSecondary = NaranjoNocheFondo,
    secondaryContainer = NaranjoNocheContenedor,
    onSecondaryContainer = NaranjoClaro,
    tertiary = MagentaNoche,
    onTertiary = MagentaNocheFondo,
    tertiaryContainer = MagentaNocheContenedor,
    onTertiaryContainer = MagentaClaro,
    background = FondoOscuro,
    onBackground = TextoClaro,
    surface = FondoOscuro,
    onSurface = TextoClaro
)

private val LightColorScheme = lightColorScheme(
    primary = Morado,
    onPrimary = Color.White,
    primaryContainer = MoradoClaro,
    onPrimaryContainer = MoradoOscuro,
    secondary = Naranjo,
    onSecondary = Color.White,
    secondaryContainer = NaranjoClaro,
    onSecondaryContainer = NaranjoOscuro,
    tertiary = Magenta,
    onTertiary = Color.White,
    tertiaryContainer = MagentaClaro,
    onTertiaryContainer = MagentaOscuro,
    background = FondoClaro,
    onBackground = TextoOscuro,
    surface = FondoClaro,
    onSurface = TextoOscuro
)

@Composable
fun AlbaLabTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}