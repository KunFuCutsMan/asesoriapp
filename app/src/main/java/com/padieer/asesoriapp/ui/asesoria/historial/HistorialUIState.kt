package com.padieer.asesoriapp.ui.asesoria.historial

import com.padieer.asesoriapp.domain.getters.AsesoriaConAsesorData

sealed interface HistorialUIState {

    data object Loading: HistorialUIState

    data class Error(
        val error: String,
    ): HistorialUIState

    sealed interface Asesorias: HistorialUIState {

        val contenido: List<AsesoriaConAsesorData>

        val estadosFilter: EstadoFilter

        val tiempoFilter: TiempoFilter

        data class NoContent(
            override val contenido: List<AsesoriaConAsesorData> = emptyList(),
            override val tiempoFilter: TiempoFilter = TiempoFilter.ALL,
            override val estadosFilter: EstadoFilter = EstadoFilter.ALL,
        ): Asesorias


        data class AsesoriasEstudiante(
            override val contenido: List<AsesoriaConAsesorData>,
            override val tiempoFilter: TiempoFilter = TiempoFilter.ALL,
            override val estadosFilter: EstadoFilter = EstadoFilter.ALL,
        ): Asesorias
    }
}

enum class EstadoFilter {
    ALL,
    PENDIENTE,
    EN_PROCESO,
    COMPLETADA,
}

enum class TiempoFilter {
    ALL,
    PASADAS,
    FUTURAS,
}