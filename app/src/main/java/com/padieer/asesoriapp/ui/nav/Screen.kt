package com.padieer.asesoriapp.ui.nav

import kotlinx.serialization.Serializable

sealed class Screen {

    @Serializable
    data object SplashScreen : Screen()

    /**
     * Grafo para la sección de Auth, que incluye las pantallas de inicio de sesión y creación
     * de cuentas
     */
    @Serializable
    data object Auth : Screen() {
        @Serializable
        data object InicioSesionScreen : Screen()

        @Serializable
        data object CreaCuentaScreen : Screen()
    }

    @Serializable
    data object App : Screen() {
        @Serializable
        data object PerfilScreen : Screen()
    }
}