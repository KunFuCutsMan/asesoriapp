package com.padieer.asesoriapp.ui.asesoria.asignarAsesor

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme

/**
 * Este compose contiene va a contener el viewmodel y cambios de estado
 * va a estar dentro de un scaffold asi que maximiza lo que este adentro
 */
@Composable
fun AsignaAsesorScreen(navController: NavController?) {
    // Se crea el viewmodel
    // Se subscribe al estado

    // Y dependiendo del estado se muestra esto

    AsignaAsesor(AsignaAsesorUIState.AsignaAsesor(""))
}

/**
 * El estado "principal" de la pantalla, a menos que ocurra un error
 * o se este cargando algo, esto es lo que se muestra
 */
@Composable
fun AsignaAsesor(state: AsignaAsesorUIState.AsignaAsesor) {
    // uwU
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun AsignaAsesorScreenPreview() {
    val state = AsignaAsesorUIState.AsignaAsesor("")
    AsesoriAppTheme {
        Scaffold(
            topBar = { TopAppBar( title = { Text("Pantalla para asignar asesores") } ) }
        ) { paddingValues ->

            Surface(Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
            ) {
                AsignaAsesor(state)
            }

        }
    }
}