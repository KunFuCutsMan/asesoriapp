package com.padieer.asesoriapp.ui.asesoria.asignarAsesor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.padieer.asesoriapp.domain.model.*
import com.padieer.asesoriapp.ui.asesoria.TarjetaAsesoria
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

// Pantalla principal de asignación de asesor
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsignaAsesorScreen(
    navController: NavController?,
    state: AsignaAsesorUIState = AsignaAsesorUIState.Loading,
    onGuardar: ((Asesoria) -> Unit)? = null
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Asignar Asesor") }) }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (state) {
                is AsignaAsesorUIState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is AsignaAsesorUIState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Error: ${state.error}")
                    }
                }
                is AsignaAsesorUIState.Success -> {
                    EditableAsesoriaCardAsignar(
                        asesoria = state.asesoria,
                        asesoresDisponibles = state.asesoresDisponibles,
                        onGuardar = onGuardar
                    )
                }
            }
        }
    }
}

// Composable de la tarjeta editable (solo campos relevantes para asignación)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditableAsesoriaCardAsignar(
    asesoria: Asesoria,
    asesoresDisponibles: List<Asesor>,
    onGuardar: ((Asesoria) -> Unit)?,
    expanded: Boolean = false
) {
    var isExpanded by remember { mutableStateOf(expanded) }
    var asesorSeleccionado by remember { mutableStateOf(asesoria.asesor) }
    var diaAsesoria by remember { mutableStateOf(asesoria.dia) }
    var horaInicio by remember { mutableStateOf(asesoria.horaInicio) }
    var horaFinal by remember { mutableStateOf(asesoria.horaFinal) }
    var expandedDropdown by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { isExpanded = !isExpanded },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Información principal
            TarjetaAsesoria(asesoria = asesoria, progreso = 1) {}

            if (isExpanded) {
                Spacer(modifier = Modifier.height(12.dp))

                // Selección de asesor
                ExposedDropdownMenuBox(
                    expanded = expandedDropdown,
                    onExpandedChange = { expandedDropdown = !expandedDropdown }
                ) {
                    TextField(
                        readOnly = true,
                        value = asesorSeleccionado?.id?.toString() ?: "Selecciona un asesor",
                        onValueChange = {},
                        label = { Text("Asesor") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDropdown) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedDropdown,
                        onDismissRequest = { expandedDropdown = false }
                    ) {
                        asesoresDisponibles.forEach { asesor ->
                            DropdownMenuItem(
                                text = { Text("Asesor ID: ${asesor.id}") },
                                onClick = {
                                    asesorSeleccionado = asesor
                                    expandedDropdown = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Fecha de la asesoría
                OutlinedTextField(
                    value = diaAsesoria.toString(),
                    onValueChange = { /* agregar DatePicker si se desea */ },
                    label = { Text("Día de la asesoría") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Hora de inicio y final
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = horaInicio.toString(),
                        onValueChange = { /* agregar TimePicker si se desea */ },
                        label = { Text("Hora inicio") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = horaFinal.toString(),
                        onValueChange = { /* agregar TimePicker si se desea */ },
                        label = { Text("Hora final") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        val nuevaAsesoria = asesoria.copy(
                            asesor = asesorSeleccionado,
                            dia = diaAsesoria,
                            horaInicio = horaInicio,
                            horaFinal = horaFinal
                        )
                        onGuardar?.invoke(nuevaAsesoria)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar cambios")
                }
            }
        }
    }
}

// Preview el bueno
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun PreviewAsignaAsesorScreenDesplegable() {
    val asesoriaEjemplo = Asesoria(
        id = 1,
        dia = LocalDate(2025, 8, 15),
        horaInicio = LocalTime(10, 0),
        horaFinal = LocalTime(11, 0),
        carrera = Carrera("Administración"),
        asignatura = Asignatura("Ecuaciones Diferenciales", 1, ""),
        estado = "En proceso",
        estudiante = Estudiante(
            nombre = "Juan",
            apePaterno = "Ladrón de Guevara",
            apeMaterno = "Lopeida",
            numeroTelefono = "1800000040",
            numeroControl = "20001987",
            semestre = 7,
            asesor = Asesor(1),
            admin = null
        ),
        asesor = Asesor(1)
    )
    val asesoresEjemplo = listOf(Asesor(1), Asesor(2), Asesor(3))

    AsesoriAppTheme {
        Scaffold(
            topBar = { TopAppBar(title = { Text("Asignar Asesor (Preview)") }) }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // Tarjeta desplegable
                var isExpanded by remember { mutableStateOf(false) }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isExpanded = !isExpanded },
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        // Información principal de la asesoría
                        TarjetaAsesoria(asesoria = asesoriaEjemplo, progreso = 1) { mod ->
                            Column(mod) {
                                Text(
                                    "Asesoría dada por: ${asesoriaEjemplo.asesor?.id ?: "Sin asignar"}",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold),
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                                Text(
                                    "Nombre del Asesorado: ${asesoriaEjemplo.estudiante.nombre}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(bottom = 2.dp)
                                )
                                Text(
                                    "Materia Correspondiente: ${asesoriaEjemplo.asignatura.nombre}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(bottom = 2.dp)
                                )
                                Text(
                                    "De la carrera de: ${asesoriaEjemplo.carrera.nombre}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(bottom = 2.dp)
                                )
                                Text(
                                    "Estado de la asesoría: ${asesoriaEjemplo.estado}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }

                        // Contenido editable solo dentro del despliegue
                        if (isExpanded) {
                            Spacer(modifier = Modifier.height(12.dp))

                            var asesorSeleccionado by remember { mutableStateOf(asesoriaEjemplo.asesor) }
                            var diaAsesoria by remember { mutableStateOf(asesoriaEjemplo.dia) }
                            var horaInicio by remember { mutableStateOf(asesoriaEjemplo.horaInicio) }
                            var horaFinal by remember { mutableStateOf(asesoriaEjemplo.horaFinal) }
                            var expandedDropdown by remember { mutableStateOf(false) }

                            // Selección de asesor
                            ExposedDropdownMenuBox(
                                expanded = expandedDropdown,
                                onExpandedChange = { expandedDropdown = !expandedDropdown }
                            ) {
                                TextField(
                                    readOnly = true,
                                    value = asesorSeleccionado?.id?.toString() ?: "Selecciona un asesor",
                                    onValueChange = {},
                                    label = { Text("Asesor") },
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDropdown) },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                ExposedDropdownMenu(
                                    expanded = expandedDropdown,
                                    onDismissRequest = { expandedDropdown = false }
                                ) {
                                    asesoresEjemplo.forEach { asesor ->
                                        DropdownMenuItem(
                                            text = { Text("Asesor ID: ${asesor.id}") },
                                            onClick = {
                                                asesorSeleccionado = asesor
                                                expandedDropdown = false
                                            }
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Fecha de la asesoría
                            OutlinedTextField(
                                value = diaAsesoria.toString(),
                                onValueChange = { /* DatePicker opcional */ },
                                label = { Text("Día de la asesoría") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Hora inicio y final
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedTextField(
                                    value = horaInicio.toString(),
                                    onValueChange = { /* TimePicker opcional */ },
                                    label = { Text("Hora inicio") },
                                    modifier = Modifier.weight(1f)
                                )
                                OutlinedTextField(
                                    value = horaFinal.toString(),
                                    onValueChange = { /* TimePicker opcional */ },
                                    label = { Text("Hora final") },
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Button(
                                onClick = {
                                    val nuevaAsesoria = asesoriaEjemplo.copy(
                                        asesor = asesorSeleccionado,
                                        dia = diaAsesoria,
                                        horaInicio = horaInicio,
                                        horaFinal = horaFinal
                                    )
                                    println("Guardar cambios: $nuevaAsesoria")
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Guardar cambios")
                            }
                        }
                    }
                }
            }
        }
    }
}
