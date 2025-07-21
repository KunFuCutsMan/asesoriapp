package com.padieer.asesoriapp.ui.nav.graph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.padieer.asesoriapp.ui.cuentas.CreaCuentaScreen
import com.padieer.asesoriapp.ui.login.InicioSesionScreen
import com.padieer.asesoriapp.ui.nav.Screen
import com.padieer.asesoriapp.ui.password.ForgotPasswordScreen

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    val tween_time = 1000;
    navigation<Screen.Auth>(
        startDestination = Screen.Auth.InicioSesionScreen,
        enterTransition = { fadeIn(tween(tween_time)) }
    ) {
        composable<Screen.Auth.InicioSesionScreen>(
            enterTransition = { slideIntoContainer (
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(tween_time)
            ) },
            exitTransition = { slideOutOfContainer (
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(tween_time)
            ) }
        ) {
            InicioSesionScreen(navController)
        }
        composable<Screen.Auth.CreaCuentaScreen>(
            enterTransition = { slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(tween_time)
            ) },
            exitTransition = { slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(tween_time)
            ) }
        ) {
            CreaCuentaScreen(navController)
        }
        composable<Screen.Auth.ForgotPasswordScreen>(
            enterTransition = { slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(tween_time)
            ) },
            exitTransition = { slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(tween_time)
            ) }
        ) {
            ForgotPasswordScreen(navController)
        }
    }
}