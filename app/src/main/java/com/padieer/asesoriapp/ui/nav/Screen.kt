package com.padieer.asesoriapp.ui.nav

import kotlinx.serialization.Serializable

sealed class Screen {

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

        @Serializable
        data object ForgotPasswordScreen: Screen()
    }

    @Serializable
    data object App : Screen()
}

sealed class AppScreen: Screen() {
    data object Usuario {
        /**
         * Pantalla inicial del usuario después que inicia sesión.
         * Da la bienvenida al usuario y muestra su asesoria más próxima, si tiene alguna
         */
        @Serializable
        data object HomeScreen : AppScreen()

        /**
         * Pantalla que muestra el perfil del usuario, si rol dentro de la aplicación
         * (estudiante, asesor, admin) y ofrece una pestaña para modificar sus datos
         */
        @Serializable
        data object PerfilScreen : AppScreen()

        @Serializable
        data class PerfilAjeno(val estudianteID: Int): AppScreen()
    }

    data object Asesoria {

        /**
         * Formulario para pedir una asesoria
         */
        @Serializable
        data object PedirAsesoriaScreen: AppScreen()

        /**
         * El estudiante puede ver un historial de asesorias que pidió
         */
        @Serializable
        data object HistorialAsesoriasScreen: AppScreen()

        /**
         * Un admin asigna un asesor a las asesorias que se pidieron
         *
         * Para cada asesoria, observa primero los asesores que pueden dar la materia,
         * y despues el resto de los asesores.
         *
         * SOLO PARA ADMINS
         */
        @Serializable
        data object AsignarAsesorScreen: AppScreen()
    }

    data object Asesor {

        /**
         * Lista las asesorias pendientes del asesor
         *
         * SOLO PARA ASESORES Y ADMINS
         */
        @Serializable
        data object AsesoriaAsesorScreen: AppScreen()

        /**
         * Formulario para cambiar la disponibilidad del asesor,
         * al asignar que horas tiene ocupadas
         *
         * SOLO PARA ASESORES Y ADMINS
         */
        @Serializable
        data object DisponibilidadAsesorScreen: AppScreen()

        /**
         *
         */
        @Serializable
        data object HistorialAsesorScreen: AppScreen()

        @Serializable
        data object EstadisticasAsesorScreen: AppScreen()
    }
}