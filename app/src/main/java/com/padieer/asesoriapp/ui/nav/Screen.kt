package com.padieer.asesoriapp.ui.nav

sealed class Screen( val route: String ) {

    data object SplashScreen : Screen("splash_screen")

    data object InicioSesionScreen : Screen("inicio_sesion_screen")

    data object CreaCuentaScreen : Screen("crea_cuenta_screen")
}