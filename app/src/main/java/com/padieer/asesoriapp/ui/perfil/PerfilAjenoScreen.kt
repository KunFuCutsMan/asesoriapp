package com.padieer.asesoriapp.ui.perfil

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.padieer.asesoriapp.di.FakeAppModule
import com.padieer.asesoriapp.ui.common.ErrorText
import com.padieer.asesoriapp.ui.common.FullScreenLoading
import com.padieer.asesoriapp.ui.common.Perfil
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme

@Composable
fun PerfilAjenoScreen(estudianteID: Int) {
    val viewModel: PerfilAjenoViewModel = viewModel( factory = PerfilAjenoViewModel.Factory(estudianteID) )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
            PerfilAjeno(
                state = uiState as PerfilUiState.EstudiantePerfil,
                onTelefonoClick = { viewModel.onEvent(PerfilUIEvent.TelefonoClick) }
            )
        }
        PerfilUiState.Loading -> {
            FullScreenLoading()
        }
    }
}

@Composable
private fun PerfilAjeno(state: PerfilUiState.EstudiantePerfil, onTelefonoClick: () -> Unit) {
    val (estudiante, carrera, especialidad) = state
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Perfil(
            estudiante = estudiante,
            carrera = carrera,
            especialidad = especialidad,
            isCallable = true,
            onTelefonoClick = onTelefonoClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PerfilAjenoScreenPreview() {
    App.appModule = FakeAppModule()
    AsesoriAppTheme {
        Scaffold(
            topBar = { TopAppBar( title = {Text("Pantalla de Perfil Ajeno")} ) }
        ) { paddingValues ->
            Surface(Modifier.padding(paddingValues).consumeWindowInsets(paddingValues)) {
                PerfilAjenoScreen(1)
            }
        }
    }
}