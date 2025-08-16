package com.padieer.asesoriapp.ui.asesoria

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme
import com.padieer.asesoriapp.ui.theme.isDarkTheme
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

// Modelos de ejemplo (Desconozco si lo vas a cambiar, pero utilice los datos de la misma forma que mandaste en wsp
data class Estudiante(val nombre: String)
data class Asesor(val nombre: String)
data class Asignatura(val nombre: String)
data class Carrera(val nombre: String)
data class Asesoria(
    val dia: LocalDate,
    val horaInicio: LocalTime,
    val horaFinal: LocalTime,
    val carrera: Carrera,
    val asignatura: Asignatura,
    val estado: String,
    val estudiante: Estudiante,
    val asesor: Asesor?
)

@Composable
fun TarjetaAsesoria(
    modifier: Modifier = Modifier,
    asesoria: Asesoria
) {
    var expanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(if (expanded) 180f else 0f, label = "")

    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    val fechaFormateada = dateFormatter.format(asesoria.dia)
    val horaInicioFormateada = timeFormatter.format(asesoria.horaInicio)
    val horaFinalFormateada = timeFormatter.format(asesoria.horaFinal)

    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(animationSpec = tween(easing = Ease, durationMillis = 500)),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
        ) {
            // Tarjeta Asesoria
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Column(
                    modifier = Modifier.weight(2f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)) {

                    Text(
                        text = asesoria.asignatura.nombre,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = asesoria.estado,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    MarcasProgresos(
                        modifier = Modifier.height(4.dp)
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Bottom
                )
                {
                    Text(
                        text = fechaFormateada,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "$horaInicioFormateada - $horaFinalFormateada",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Mostrar más",
                            modifier = Modifier.rotate(rotation),
                        )
                    }
                }
            }

        }

        // Parte desplegable
        if (expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Asesoría dada por: ${asesoria.asesor?.nombre ?: "Sin asignar"}",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Nombre del Asesorado: ${asesoria.estudiante.nombre}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = "Materia Correspondiente: ${asesoria.asignatura.nombre}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = "De la carrera de: ${asesoria.carrera.nombre}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = "Estado de la asesoría: ${asesoria.estado}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        }

    }
}

@Composable
fun MarcasProgresos(modifier: Modifier = Modifier) {
    val activeColor = if (isDarkTheme()) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.secondary
    val unactiveColor = if (isDarkTheme()) MaterialTheme.colorScheme.surfaceBright else MaterialTheme.colorScheme.tertiaryContainer
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {

        Box(Modifier.fillMaxHeight().weight(4f).background(activeColor))

        Spacer(Modifier.weight(1f))

        Box(Modifier.fillMaxHeight().weight(4f).background(unactiveColor))

        Spacer(Modifier.weight(1f))

        Box(Modifier.fillMaxHeight().weight(4f).background(unactiveColor))
    }
}

// Preview para la tarjeta de asesoría
@Preview
@Composable
fun PreviewTarjetaAsesoria() {
    val asesoriaEjemplo = Asesoria(
        dia = LocalDate.now(),
        horaInicio = LocalTime.of(10, 0),
        horaFinal = LocalTime.of(11, 0),
        carrera = Carrera("Ingeniería en Sistemas"),
        asignatura = Asignatura("\nEcuaciones Diferenciales"),
        estado = "Activa",
        estudiante = Estudiante("Luis Romero"),
        asesor = Asesor("Raúl Alberto")
    )
    AsesoriAppTheme(false) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(500.dp),
            contentAlignment = Alignment.Center
        ) {
            TarjetaAsesoria(
                modifier = Modifier.padding(16.dp),
                asesoria = asesoriaEjemplo
            )
        }
    }
}
// xd