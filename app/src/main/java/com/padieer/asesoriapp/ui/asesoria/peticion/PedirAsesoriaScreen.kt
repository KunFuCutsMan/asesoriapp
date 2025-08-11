package com.padieer.asesoriapp.ui.asesoria.peticion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.padieer.asesoriapp.ui.common.FullScreenLoading
import com.padieer.asesoriapp.ui.common.ModalDatePickerField
import com.padieer.asesoriapp.ui.common.OutlinedDropdown
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

@Composable
fun PedirAsesoriaScreen() {
    val viewModel: PedirAsesoriaViewModel = viewModel( factory = PedirAsesoriaViewModel.factory() )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        PedirAsesoriaUIState.Loading -> {
            FullScreenLoading()
        }
        is PedirAsesoriaUIState.PedirAsesoria -> {
            PedirAsesoria(
                state = uiState as PedirAsesoriaUIState.PedirAsesoria,
                onAsignaturaValueChange = {},
                onSubmitClick = {},
            )
        }
    }
}

@Composable
fun PedirAsesoria(
    modifier: Modifier = Modifier, state: PedirAsesoriaUIState.PedirAsesoria,
    onAsignaturaValueChange: (Int) -> Unit,
    onSubmitClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {

        Spacer(Modifier.weight(1f))

        OutlinedDropdown(
            modifier = Modifier.fillMaxWidth(),
            data = state.asignaturas,
            label = { Text("Asesoría") },
            onValueChange = onAsignaturaValueChange
        )

        Card(
            colors = CardDefaults.cardColors().copy(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                ModalDatePickerField(
                    value = null,
                    onValueChange = {},
                    label = "Fecha",
                )

                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Column(Modifier.weight(1f)) {
                        Text("De...")
                        PlaceholderBox(20.dp)
                    }
                    Column(Modifier.weight(1f)) {
                        Text("Hasta...")
                        PlaceholderBox(20.dp)
                    }
                }
            }
        }

        Spacer(Modifier.weight(1f))

        Button(onClick = onSubmitClick) {
            Text("Pedir Asesoría")
        }
    }
}

@Composable
private fun PlaceholderBox(height: Dp) {
    Box(Modifier.fillMaxWidth().height(height).background(color = Color.Red))
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PedirAsesoriaPreview() {
    AsesoriAppTheme {
        Scaffold(
            topBar = { TopAppBar( title = { Text("Pantalla para pedir asesoria") } ) }
        ) { paddingValues ->
            val state = PedirAsesoriaUIState.PedirAsesoria(
                asignaturas = List(5) { "Asignatura $it" },
                dia = LocalDate(1,1,1),
                horaInicio = LocalTime(1,1,1),
                horaFinal = LocalTime(1,1,1),
            )

            Surface(Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
            ) {
                PedirAsesoria(
                    state = state,
                    onAsignaturaValueChange = {},
                    onSubmitClick = {},
                )
            }

        }
    }
}