package com.padieer.asesoriapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.padieer.asesoriapp.ui.nav.Navigation
import com.padieer.asesoriapp.ui.nav.Screen
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>( factoryProducer = { MainViewModel.Factory() } )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.state.value.isReady
            }
        }
        enableEdgeToEdge()
        setContent {
            val state by viewModel.state.collectAsStateWithLifecycle()
            AsesoriAppTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize() ) {
                    val screen = if (state.isLoggedIn) Screen.App else Screen.Auth
                    Navigation(screen)
                }
            }
        }
    }
}