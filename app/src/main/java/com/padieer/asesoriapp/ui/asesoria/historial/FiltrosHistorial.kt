package com.padieer.asesoriapp.ui.asesoria.historial

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme

@Composable
fun FiltrosHistorial(
    modifier: Modifier = Modifier,
    filtros: HistorialUIState.Asesorias,
    onEstadoFilterChange: (EstadoFilter) -> Unit,
    onTiempoFilterChange: (TiempoFilter) -> Unit) {

    val tiempoSeleccionado = when (filtros.tiempoFilter) {
        TiempoFilter.ALL -> "Todas"
        TiempoFilter.PASADAS -> "Anteriores"
        TiempoFilter.FUTURAS -> "Futuras"
        TiempoFilter.HOY -> "De hoy"
    }

    var tiempoChipOpen by remember { mutableStateOf(false) }
    val tiempoChipIconRotation by animateFloatAsState( if (tiempoChipOpen) 180f else 0f )

    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()).animateContentSize(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)) {

        Spacer(Modifier.weight(0.01f))
        Box(contentAlignment = Alignment.BottomStart) {
            FilterChip(
                modifier = Modifier.animateContentSize(),
                selected = false,
                label = { Text(tiempoSeleccionado) },
                onClick = { tiempoChipOpen = !tiempoChipOpen },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Abrir opciones de tiempo",
                        modifier = Modifier.rotate(tiempoChipIconRotation)
                    )
                }
            )

            DropdownMenu(
                expanded = tiempoChipOpen,
                onDismissRequest = { tiempoChipOpen = false },

            ) {
                DropdownMenuItem(
                    text = { Text("Todas") },
                    onClick = { tiempoChipOpen = false; onTiempoFilterChange(TiempoFilter.ALL) }
                )
                DropdownMenuItem(
                    text = { Text("Hoy") },
                    onClick = { tiempoChipOpen = false; onTiempoFilterChange(TiempoFilter.HOY) }
                )
                DropdownMenuItem(
                    text = { Text("Futuras") },
                    onClick = { tiempoChipOpen = false; onTiempoFilterChange(TiempoFilter.FUTURAS) }
                )
                DropdownMenuItem(
                    text = { Text("Anteriores") },
                    onClick = { tiempoChipOpen = false; onTiempoFilterChange(TiempoFilter.PASADAS) }
                )
            }
        }
        FilterChip(
                selected = filtros.estadosFilter == EstadoFilter.PENDIENTE,
                label = { Text("Pendientes") },
                onClick = { onEstadoFilterChange(EstadoFilter.PENDIENTE) }
            )
        FilterChip(
                selected = filtros.estadosFilter == EstadoFilter.EN_PROCESO,
                label = { Text("En proceso") },
                onClick = { onEstadoFilterChange(EstadoFilter.EN_PROCESO) }
        )
        FilterChip(
                selected = filtros.estadosFilter == EstadoFilter.COMPLETADA,
                label = { Text("Completadas") },
                onClick = { onEstadoFilterChange(EstadoFilter.COMPLETADA) }
        )
        FilterChip(
            selected = filtros.estadosFilter == EstadoFilter.CANCELADA,
            label = { Text("Canceladas") },
            onClick = { onEstadoFilterChange(EstadoFilter.CANCELADA) }
        )
        Spacer(Modifier.weight(0.01f))
    }
}

@Preview
@Composable
private fun FiltrosHistorialPreview() {
    var filtros by remember { mutableStateOf(HistorialUIState.Asesorias.NoContent()) }
    AsesoriAppTheme {
        Surface(Modifier.fillMaxWidth()) {
            FiltrosHistorial(
                modifier = Modifier.padding(vertical = 50.dp),
                filtros = filtros,
                onEstadoFilterChange = {
                    val nuevoEstado = if (filtros.estadosFilter == it) EstadoFilter.ALL else it
                    filtros = filtros.copy(estadosFilter = nuevoEstado)
                },
                onTiempoFilterChange = {
                    filtros = filtros.copy(tiempoFilter = it)
                },
            )
        }
    }
}