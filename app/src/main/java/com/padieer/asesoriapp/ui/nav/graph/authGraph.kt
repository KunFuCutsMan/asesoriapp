package com.padieer.asesoriapp.ui.nav.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.padieer.asesoriapp.ui.cuentas.CreaCuentaScreen
import com.padieer.asesoriapp.ui.login.InicioSesionScreen
import com.padieer.asesoriapp.ui.nav.Screen

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation<Screen.Auth>( startDestination = Screen.Auth.InicioSesionScreen ) {
        composable<Screen.Auth.InicioSesionScreen> {
            InicioSesionScreen(navController)
        }
        composable<Screen.Auth.CreaCuentaScreen> {
            CreaCuentaScreen(navController)
        }
    }
}