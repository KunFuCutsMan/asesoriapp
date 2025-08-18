package com.padieer.asesoriapp.ui.perfil

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.padieer.asesoriapp.App
import com.padieer.asesoriapp.data.estudiante.EstudianteRepository
import com.padieer.asesoriapp.di.FakeAppModule
import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.model.EstudianteModel
import com.padieer.asesoriapp.ui.common.ErrorText
import com.padieer.asesoriapp.ui.common.FullScreenLoading
import com.padieer.asesoriapp.ui.common.Perfil
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme

@Composable
fun PerfilScreen() {
    val viewModel: PerfilViewModel = viewModel( factory = PerfilViewModel.Factory() )
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    when (uiState) {
        is PerfilUiState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                ErrorText(
                    text = (uiState as PerfilUiState.Error).error,
                    textAlign = TextAlign.Center
                )
            }
        }
        is PerfilUiState.EstudiantePerfil -> {
            PerfilDeEstudiante(uiState as PerfilUiState.EstudiantePerfil)
        }
        PerfilUiState.Loading -> {
            FullScreenLoading()
        }
    }
}

@Composable
fun PerfilDeEstudiante(uiState: PerfilUiState.EstudiantePerfil) {
    val (estudiante, carrera, especialidad) = uiState
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Perfil(
            estudiante = estudiante,
            carrera = carrera,
            especialidad = especialidad,
        )

        Spacer(Modifier.weight(1f, true))

        TextButton(onClick = {}) {
            Text("Editar Datos", style = MaterialTheme.typography.labelLarge)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PerfilScreenPreview() {
    App.appModule = FakeAppModule()

    AsesoriAppTheme {
        Scaffold(
            topBar = { TopAppBar( title = {Text("Pantalla de Perfil")} ) }
        ) { paddingValues ->
            Surface(Modifier.padding(paddingValues).consumeWindowInsets(paddingValues)) {
                PerfilScreen()
            }
        }
    }
}