package com.padieer.asesoriapp.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.padieer.asesoriapp.ui.SplashScreen
import com.padieer.asesoriapp.ui.nav.graph.authNavGraph

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost( navController = navController, startDestination = Screen.SplashScreen ) {
        composable<Screen.SplashScreen> {
            SplashScreen(navController)
        }

        // Grafo de Auth
        authNavGraph(navController)

        // Grafo de App
        composable<Screen.App> {
            ApplicationNavigationDrawer()
        }

    }
}