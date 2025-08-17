package com.padieer.asesoriapp.ui.nav.graph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.padieer.asesoriapp.ui.asesoria.asignarAsesor.AsignaAsesorScreen
import com.padieer.asesoriapp.ui.asesoria.historial.HistorialEstudianteScreen
import com.padieer.asesoriapp.ui.asesoria.peticion.PedirAsesoriaScreen
import com.padieer.asesoriapp.ui.disponibilidad.DisponibilidadAsesorScreen
import com.padieer.asesoriapp.ui.nav.AppScreen
import com.padieer.asesoriapp.ui.perfil.PerfilAjenoScreen
import com.padieer.asesoriapp.ui.perfil.PerfilScreen

@Composable
fun AppGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.Usuario.HomeScreen,
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(500)) },
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(500)) },
        popEnterTransition = { fadeIn(tween(500)) },
        popExitTransition = { fadeOut(tween(500)) },
    ) {
        composable<AppScreen.Usuario.HomeScreen> {
            Text("Pantalla de Usuario")
        }
        composable<AppScreen.Usuario.PerfilScreen> {
            PerfilScreen()
        }

        composable<AppScreen.Asesoria.PedirAsesoriaScreen> {
            PedirAsesoriaScreen(navController)
        }
        composable<AppScreen.Asesoria.HistorialAsesoriasScreen>(
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(500)) },
            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(500)) }
        ) {
            HistorialEstudianteScreen(navController)
        }
        composable<AppScreen.Asesoria.AsignarAsesorScreen> {
            AsignaAsesorScreen(navController)
        }

        composable<AppScreen.Asesor.AsesoriaAsesorScreen> {
            Text("Pantalla que lista las asesorias del asesor pendiente")
        }
        composable<AppScreen.Asesor.DisponibilidadAsesorScreen> {
            DisponibilidadAsesorScreen()
        }
        composable<AppScreen.Asesor.HistorialAsesorScreen> {
            Text("Pantalla que muestra el historial de asesorias del asesor")
        }
        composable<AppScreen.Asesor.EstadisticasAsesorScreen> {
            Text("Pantalla que muestra las estadisticas del asesor")
        }

        /* Pantallas no accesibles desde el AppDrawer */
        composable<AppScreen.Usuario.PerfilAjeno>(
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(500)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(500)) }
        ) {
            val data: AppScreen.Usuario.PerfilAjeno = it.toRoute()
            PerfilAjenoScreen(estudianteID = data.estudianteID)
        }
    }
}