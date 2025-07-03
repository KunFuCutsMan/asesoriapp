package com.padieer.asesoriapp.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.padieer.asesoriapp.ui.SplashScreen
import com.padieer.asesoriapp.ui.cuentas.CreaCuentaScreen
import com.padieer.asesoriapp.ui.login.InicioSesionScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost( navController = navController, startDestination = Screen.SplashScreen.route ) {
        composable( route = Screen.SplashScreen.route ) {
            SplashScreen(navController)
        }

        composable( route = Screen.InicioSesionScreen.route ) {
            InicioSesionScreen(navController)
        }

        composable( route = Screen.CreaCuentaScreen.route ) {
            CreaCuentaScreen(navController)
        }
    }
}