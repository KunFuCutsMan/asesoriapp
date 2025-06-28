package com.padieer.asesoriapp.ui.nav

sealed class Screen( val route: String ) {

    object SplashScreen : Screen("splash_screen")

    object InicioSesionScreen : Screen("inicio_sesion_screen")
}