package com.padieer.asesoriapp.ui.nav.graph

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.padieer.asesoriapp.ui.nav.AppScreen

@Composable
fun AppGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = AppScreen.Usuario.HomeScreen) {
        composable<AppScreen.Usuario.HomeScreen> {
            Text("Pantalla de Usuario")
        }
        composable<AppScreen.Usuario.PerfilScreen> {
            Text("Pantalla de Perfil")
        }

        composable<AppScreen.Asesoria.PedirAsesoriaScreen> {
            Text("Pantalla para pedir una asesoría")
        }
        composable<AppScreen.Asesoria.HistorialAsesoriasScreen> {
            Text("Pantalla para ver el historial de asesorias")
        }
        composable<AppScreen.Asesoria.AsignarAsesorScreen> {
            Text("Pantalla para que un admin asigne un asesor a las asesorias pendientes")
        }

        composable<AppScreen.Asesor.AsesoriaAsesorScreen> {
            Text("Pantalla que lista las asesorias del asesor pendiente")
        }
        composable<AppScreen.Asesor.DisponibilidadAsesorScreen> {
            Text("Pantalla que permite al asesor modificar su disponibilidad")
        }
        composable<AppScreen.Asesor.HistorialAsesorScreen> {
            Text("Pantalla que muestra el historial de asesorias del asesor")
        }
        composable<AppScreen.Asesor.EstadisticasAsesorScreen> {
            Text("Pantalla que muestra las estadisticas del asesor")
        }
    }
}

/*
NavHost(
        navController = navController,
        route = Screen.App,
        startDestination = AppScreen.Usuario.HomeScreen,
    ) {

    }
 */