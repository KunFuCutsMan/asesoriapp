package com.padieer.asesoriapp.ui.asesoria.peticion

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.padieer.asesoriapp.domain.datetime.AllowedDateValidator
import com.padieer.asesoriapp.domain.model.Asignatura
import com.padieer.asesoriapp.ui.asesoria.peticion.PedirAsesoriaEvent.*
import com.padieer.asesoriapp.ui.common.ErrorText
import com.padieer.asesoriapp.ui.common.FullScreenLoading
import com.padieer.asesoriapp.ui.common.ModalDatePickerField
import com.padieer.asesoriapp.ui.common.ModalHourTimePicker
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
                onAsignaturaValueChange = { viewModel.onEvent(AsesoriaIndexChange(it))},
                onFechaValueChange = {viewModel.onEvent(FechaChange(it))},
                onHoraInicioValueChange = {viewModel.onEvent(HoraInicioChange(it))},
                onHoraFinalValueChange = {viewModel.onEvent(HoraFinalChange(it))},
                onSubmitClick = {viewModel.onEvent(Submit)},
            )
        }
        is PedirAsesoriaUIState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                val error = uiState as PedirAsesoriaUIState.Error
                ErrorText(error.error, textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun PedirAsesoria(
    modifier: Modifier = Modifier, state: PedirAsesoriaUIState.PedirAsesoria,
    onAsignaturaValueChange: (Int) -> Unit = {},
    onFechaValueChange: (LocalDate) -> Unit = {},
    onHoraInicioValueChange: (Int) -> Unit = {},
    onHoraFinalValueChange: (Int) -> Unit = {},
    onSubmitClick: () -> Unit = {},
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
            data = state.asignaturas.map { it.nombre },
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
                    value = state.dia,
                    onValueChange = onFechaValueChange,
                    label = "Fecha",
                    selectableDates = AllowedDateValidator.All(
                        AllowedDateValidator.WeekDaysOnly,
                        AllowedDateValidator.AfterOrEqualToday,
                        AllowedDateValidator.UntilNextMonth,
                    )
                )

                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Column(Modifier.weight(1f)) {
                        Text("Desde...")
                        ModalHourTimePicker(
                            label = "Hora",
                            hourState = state.horaInicio.hour,
                            from = 7,
                            to = 20,
                            onValueChange = onHoraInicioValueChange
                        )
                    }
                    Column(Modifier.weight(1f)) {
                        Text("Hasta...")
                        ModalHourTimePicker(
                            label = "Hora",
                            hourState = state.horaFinal.hour,
                            from = 8,
                            to = 21,
                            onValueChange = onHoraFinalValueChange
                        )
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PedirAsesoriaPreview() {
    AsesoriAppTheme {
        Scaffold(
            topBar = { TopAppBar( title = { Text("Pantalla para pedir asesoria") } ) }
        ) { paddingValues ->
            val state = PedirAsesoriaUIState.PedirAsesoria(
                asignaturas = List(5) { Asignatura("Asignatura $it") },
                dia = LocalDate(1,1,1),
                horaInicio = LocalTime(9,0,0),
                horaFinal = LocalTime(10,0,0),
            )

            Surface(Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
            ) {
                PedirAsesoria(state = state)
            }

        }
    }
}