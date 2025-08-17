package com.padieer.asesoriapp.ui.asesoria.historial

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
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
import com.padieer.asesoriapp.domain.model.Estudiante
import com.padieer.asesoriapp.domain.model.toUIModel
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
            HistorialEstudiante(
                asesorias = uiState as HistorialUIState.Asesorias,
                onAsesorProfileClick = { viewModel.onEvent(HistorialUIEvent.ProfileClick(it)) },
                onEstadoFilterChange = { viewModel.onEvent(HistorialUIEvent.EstadoFilterChange(it)) },
                onTiempoFilterChange = { viewModel.onEvent(HistorialUIEvent.TiempoFilterChange(it)) }
            )
        }
    }
}

@Composable
private fun HistorialEstudiante(
    asesorias: HistorialUIState.Asesorias,
    onAsesorProfileClick: (Int) -> Unit,
    onEstadoFilterChange: (EstadoFilter) -> Unit,
    onTiempoFilterChange: (TiempoFilter) -> Unit) {

    Column {
        FiltrosHistorial(
            filtros = asesorias,
            onEstadoFilterChange = onEstadoFilterChange,
            onTiempoFilterChange = onTiempoFilterChange )

        if (asesorias is HistorialUIState.Asesorias.NoContent) {
            Box(
                Modifier.fillMaxWidth().weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay nada")
            }
        }

        if (asesorias is HistorialUIState.Asesorias.AsesoriasEstudiante)
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(asesorias.contenido) { item ->
                    TarjetaAsesoria(
                        asesoria = item.asesoria.toUIModel(),
                        progreso = when (item.estadoAsesoria.id) {
                            1 -> 1 // Pendiente
                            2 -> 2 // En Progreso
                            3 -> 3 // Terminada
                            else -> 0
                        }
                    ) { mod ->
                        if (item.asesorData != null) {
                            DatosDelAsesor(
                                modifier = mod,
                                asesorData = item.asesorData.toUIModel(),
                                onProfileClick = { onAsesorProfileClick(item.asesorData.id) }
                            )
                        }
                        else {
                            Box(mod) {
                                Text("Todavía no se ha asignado un asesor", textAlign = TextAlign.Center)
                            }
                        }
                    }
                }
            }


    }
}

@Composable
private fun DatosDelAsesor(modifier: Modifier = Modifier, asesorData: Estudiante, onProfileClick: () -> Unit) {
    val nombreCompleto = "${asesorData.nombre} ${asesorData.apePaterno} ${asesorData.apeMaterno}"
    Row(modifier, verticalAlignment = Alignment.Bottom) {
        Column(Modifier.weight(1f)) {
            Label("Asesor", nombreCompleto)
            Label("No. Control", asesorData.numeroControl)
            Label("Semestre", asesorData.semestre.toString())
        }
        IconButton(onClick = onProfileClick) {
            Icon(Icons.Outlined.AccountCircle, "")
        }
    }
}

@Composable
private fun Label(label: String, data: String) {
    Row {
        Text("$label: ", style = MaterialTheme.typography.labelLarge)
        Text(data, style = MaterialTheme.typography.bodyMedium)
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
            Surface(Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)) {
                HistorialEstudianteScreen(null)
            }
        }
    }
}

@Preview
@Composable
private fun DatosDelAsesorPreview() {
    AsesoriAppTheme {
        OutlinedCard {
            Column {
                Box(Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer))
                DatosDelAsesor(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    asesorData = Estudiante(
                        nombre = "Juan",
                        apeMaterno = "Perez",
                        apePaterno = "Camanei",
                        numeroTelefono = "1800202020",
                        numeroControl = "20001000",
                        semestre = 7,
                        asesor = null,
                        admin = null,
                    ),
                    onProfileClick = {}
                )
            }
        }
    }
}