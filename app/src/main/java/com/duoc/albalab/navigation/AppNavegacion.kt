package com.duoc.albalab.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.duoc.albalab.ui.colaborar.PantallaColaborar
import com.duoc.albalab.ui.inicio.PantallaInicio
import com.duoc.albalab.ui.nosotros.PantallaNosotros
import com.duoc.albalab.ui.perfil.PantallaPerfil
import com.duoc.albalab.ui.tienda.PantallaTienda

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavegacion() {
    val navController = rememberNavController()
    val entradaActual by navController.currentBackStackEntryAsState()
    val rutaActual = entradaActual?.destination?.route

    // Titulo de la barra superior segun la pantalla abierta
    val titulo = pantallasMenu.find { it.ruta == rutaActual }?.titulo ?: "Alba Lab"

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(titulo) })
        },
        bottomBar = {
            NavigationBar {
                pantallasMenu.forEach { pantalla ->
                    NavigationBarItem(
                        selected = rutaActual == pantalla.ruta,
                        onClick = { irA(navController, pantalla.ruta) },
                        icon = { Icon(pantalla.icono, contentDescription = pantalla.titulo) },
                        label = { Text(pantalla.titulo) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Pantalla.Inicio.ruta,
            modifier = Modifier.padding(innerPadding),
            // Animacion al cambiar de pantalla (RNF07)
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            composable(Pantalla.Inicio.ruta) {
                PantallaInicio(onQuieroAyudar = { irA(navController, Pantalla.Colaborar.ruta) })
            }
            composable(Pantalla.Nosotros.ruta) { PantallaNosotros() }
            composable(Pantalla.Tienda.ruta) { PantallaTienda() }
            composable(Pantalla.Colaborar.ruta) { PantallaColaborar() }
            composable(Pantalla.Perfil.ruta) { PantallaPerfil() }
        }
    }
}

// Navega a una pantalla del menu sin apilar pantallas repetidas
fun irA(navController: NavController, ruta: String) {
    navController.navigate(ruta) {
        popUpTo(Pantalla.Inicio.ruta) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
