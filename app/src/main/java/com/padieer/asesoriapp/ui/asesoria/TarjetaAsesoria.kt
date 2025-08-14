package com.padieer.asesoriapp.ui.asesoria

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            // Tarjeta Asesoria
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = asesoria.asignatura.nombre,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = asesoria.estado,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    Box(
                        modifier = Modifier
                            .height(6.dp)
                            .fillMaxWidth(0.4f)
                            .clip(CircleShape)
                            // Esto lo que hace, es que si el estado de la asesoria es Activa, se cambia el color a verde.
                            .background(
                                if (asesoria.estado.equals("Activa", ignoreCase = true)) Color(0xFF4CAF50)
                                else Color.Gray
                            )
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = fechaFormateada,
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                    Text(
                        text = "$horaInicioFormateada - $horaFinalFormateada",
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Mostrar más",
                            modifier = Modifier.rotate(rotation),
                            tint = Color.Black
                        )
                    }
                }
            }

            // Parte desplegable
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF0F0F0))
                    .animateContentSize()
                    .padding(16.dp)
            ) {
                if (expanded) {
                    Divider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

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
}

// Preview para la tarjeta de asesoría
@Preview(showBackground = true)
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
    TarjetaAsesoria(asesoria = asesoriaEjemplo)
}
// xd