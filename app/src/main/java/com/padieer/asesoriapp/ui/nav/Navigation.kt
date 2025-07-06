package com.padieer.asesoriapp.ui.nav

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.padieer.asesoriapp.ui.SplashScreen
import com.padieer.asesoriapp.ui.cuentas.CreaCuentaScreen
import com.padieer.asesoriapp.ui.login.InicioSesionScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost( navController = navController, startDestination = Screen.SplashScreen ) {
        composable<Screen.SplashScreen> {
            SplashScreen(navController)
        }

        // Grafo de Auth
        navigation<Screen.Auth>( startDestination = Screen.Auth.InicioSesionScreen ) {
            composable<Screen.Auth.InicioSesionScreen> {
                InicioSesionScreen(navController)
            }
            composable<Screen.Auth.CreaCuentaScreen> {
                CreaCuentaScreen(navController)
            }
        }

        // Grafo de App
        navigation<Screen.App>(startDestination = Screen.App.PerfilScreen) {
            composable<Screen.App.PerfilScreen> {
                Box(Modifier.fillMaxSize()) {
                    Text("El perfil del estudiante", modifier = Modifier.align(Alignment.Center))
                }
            }
        }

    }
}