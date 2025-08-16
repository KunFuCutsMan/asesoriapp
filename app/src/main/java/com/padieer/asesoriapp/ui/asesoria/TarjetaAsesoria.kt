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
import androidx.compose.foundation.layout.aspectRatio
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
import com.padieer.asesoriapp.domain.model.Asesor
import com.padieer.asesoriapp.domain.model.Asesoria
import com.padieer.asesoriapp.domain.model.Asignatura
import com.padieer.asesoriapp.domain.model.Carrera
import com.padieer.asesoriapp.domain.model.Estudiante
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme
import com.padieer.asesoriapp.ui.theme.isDarkTheme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format.char

@Composable
fun TarjetaAsesoria(
    modifier: Modifier = Modifier,
    asesoria: Asesoria,
    progreso: Int = 0,
    expandedContent: @Composable (Modifier) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(if (expanded) 180f else 0f, label = "")

    val dateFormatter = LocalDate.Format { day(); char('/'); monthNumber(); char('/'); year() }
    val timeFormatter = LocalTime.Format { hour(); char(':'); minute() }

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
                        modifier = Modifier.height(4.dp),
                        progreso = progreso
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
            expandedContent.invoke(Modifier.fillMaxWidth().padding(16.dp))
        }

    }
}

@Composable
fun MarcasProgresos(modifier: Modifier = Modifier, progreso: Int = 0) {
    val activeColor = if (isDarkTheme()) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.tertiary
    val unactiveColor = if (isDarkTheme()) MaterialTheme.colorScheme.surfaceBright else MaterialTheme.colorScheme.tertiaryContainer
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {

        Box(Modifier
            .fillMaxHeight()
            .weight(4f)
            .background(if (progreso > 0) activeColor else unactiveColor))

        Spacer(Modifier.weight(1f))

        Box(Modifier
            .fillMaxHeight()
            .weight(4f)
            .background(if (progreso > 1) activeColor else unactiveColor))

        Spacer(Modifier.weight(1f))

        Box(Modifier
            .fillMaxHeight()
            .weight(4f)
            .background(if (progreso > 2) activeColor else unactiveColor))
    }
}

// Preview para la tarjeta de asesoría
@Preview
@Composable
fun PreviewTarjetaAsesoria() {
    val asesoriaEjemplo = Asesoria(
        id = 1,
        dia = LocalDate(2025, 8, 15),
        horaInicio = LocalTime(10, 0, 0),
        horaFinal = LocalTime(11, 0, 0),
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
    AsesoriAppTheme(true) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .height(500.dp)
                .aspectRatio(1f),
            contentAlignment = Alignment.Center
        ) {
            TarjetaAsesoria(
                modifier = Modifier.padding(16.dp),
                asesoria = asesoriaEjemplo,
                progreso = 2
            ) { mod ->
                Column(mod)
                {
                    Text(
                        text = "Asesoría dada por: ${asesoriaEjemplo.asesor?.id ?: "Sin asignar"}",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Nombre del Asesorado: ${asesoriaEjemplo.estudiante.nombre}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "Materia Correspondiente: ${asesoriaEjemplo.asignatura.nombre}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "De la carrera de: ${asesoriaEjemplo.carrera.nombre}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "Estado de la asesoría: ${asesoriaEjemplo.estado}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
// xd