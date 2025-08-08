package com.padieer.asesoriapp.ui.disponibilidad

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme

@Composable
fun DisponibilidadAsesorScreen() {
    val viewModel = viewModel<DisponibilidadViewModel>( factory = DisponibilidadViewModel.Factory() )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when(uiState) {
        is DisponibilidadUIState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                ErrorText(
                    text = (uiState as DisponibilidadUIState.Error).error,
                    textAlign = TextAlign.Center
                )
            }
        }
        DisponibilidadUIState.Loading -> {
            FullScreenLoading()
        }
        is DisponibilidadUIState.Disponibilidad -> {
            DisponibilidadDeAsesor(
                state = uiState as DisponibilidadUIState.Disponibilidad,
                onHoraLunesClick = { viewModel.onEvent(DisponibilidadEvent.HoraLunesClick(it)) },
                onHoraMartesClick = { viewModel.onEvent(DisponibilidadEvent.HoraMartesClick(it)) },
                onHoraMiercolesClick = { viewModel.onEvent(DisponibilidadEvent.HoraMiercolesClick(it)) },
                onHoraJuevesClick = { viewModel.onEvent(DisponibilidadEvent.HoraJuevesClick(it)) },
                onHoraViernesClick = { viewModel.onEvent(DisponibilidadEvent.HoraViernesClick(it)) },
                onEditarDisponibilidadClick = { viewModel.onEvent(DisponibilidadEvent.EditaDisponibilidadClick) },
            )
        }
    }
}

val instrucciones = """
    Presiona cada casilla con las horas en las que te encuentras ocupado por alguna clase, se te asignarán asesorias en las horas en las que estés libre.
""".trimIndent()

@Composable
fun DisponibilidadDeAsesor(
    modifier: Modifier = Modifier,
    state: DisponibilidadUIState.Disponibilidad,
    onHoraLunesClick: (Int) -> Unit,
    onHoraMartesClick: (Int) -> Unit,
    onHoraMiercolesClick: (Int) -> Unit,
    onHoraJuevesClick: (Int) -> Unit,
    onHoraViernesClick: (Int) -> Unit,
    onEditarDisponibilidadClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Text(instrucciones, modifier = Modifier.padding(horizontal = 16.dp))

        HorarioDia(dia = "Lunes", horario = state.lunes, onHoraClick = onHoraLunesClick)
        HorarioDia(dia = "Martes", horario = state.martes, onHoraClick = onHoraMartesClick)
        HorarioDia(dia = "Miercoles", horario = state.miercoles, onHoraClick = onHoraMiercolesClick)
        HorarioDia(dia = "Jueves", horario = state.jueves, onHoraClick = onHoraJuevesClick)
        HorarioDia(dia = "Viernes", horario = state.viernes, onHoraClick = onHoraViernesClick)

        Spacer(Modifier.weight(1f, true))

        Button(onClick = onEditarDisponibilidadClick) { Text("Editar Disponibilidad") }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun DisponibilidadAsesorScreenPreview() {
    App.appModule = FakeAppModule()
    AsesoriAppTheme {
        Scaffold(
            topBar = {
                TopAppBar( title = { Text("Pantalla de Disponibilidad de Asesor") } )
            }
        ) { paddingValues ->
            Surface(Modifier.fillMaxSize().padding(paddingValues).consumeWindowInsets(paddingValues)) {
                DisponibilidadAsesorScreen()
            }
        }
    }
}