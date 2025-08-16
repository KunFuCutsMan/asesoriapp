package com.padieer.asesoriapp.ui.asesoria.historial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.padieer.asesoriapp.App
import com.padieer.asesoriapp.di.FakeAppModule
import com.padieer.asesoriapp.ui.asesoria.TarjetaAsesoria
import com.padieer.asesoriapp.ui.common.ErrorText
import com.padieer.asesoriapp.ui.common.FullScreenLoading
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme

@Composable
fun HistorialEstudianteScreen(navController: NavController?) {
    val viewModel = viewModel<HistorialEstudianteViewModel>( factory = HistorialEstudianteViewModel.Factory() )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    LaunchedEffect(true) {
        viewModel.navigator.channel.collect { 
            viewModel.navigator.consumeAction(it, navController, context)
        }
    }

    when (uiState) {
        is HistorialUIState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                ErrorText((uiState as HistorialUIState.Error).error, textAlign = TextAlign.Center)
            }
        }

        HistorialUIState.Loading -> {
            FullScreenLoading()
        }

        is HistorialUIState.Asesorias -> {
            HistorialEstudiante(asesorias = uiState as HistorialUIState.Asesorias)
        }
    }
}

@Composable
private fun HistorialEstudiante(asesorias: HistorialUIState.Asesorias) {

    if (asesorias is HistorialUIState.Asesorias.NoContent) {

        return
    }

    Column {
        Text("Historial de estudiante")
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(asesorias.contenido) { item ->
                TarjetaAsesoria(
                    asesoria = item
                ) {
                    Text("Algo mas")
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun HistorialEstudianteScreenPreview() {
    App.appModule = FakeAppModule()
    AsesoriAppTheme {
        Scaffold(
            topBar = { TopAppBar( title = { Text("Historial de Asesorias del estudiante") } ) }
        ) { paddingValues ->
            Surface(Modifier.fillMaxSize().padding(paddingValues).consumeWindowInsets(paddingValues)) {
                HistorialEstudianteScreen(null)
            }
        }
    }
}