package com.padieer.asesoriapp.ui.asesoria.historial

import com.padieer.asesoriapp.domain.getters.AsesoriaConAsesorData

sealed interface HistorialUIState {

    data object Loading: HistorialUIState

    data class Error(
        val error: String,
    ): HistorialUIState

    sealed interface Asesorias: HistorialUIState {

        val contenido: List<AsesoriaConAsesorData>

        data class NoContent(
            override val contenido: List<AsesoriaConAsesorData> = emptyList()
        ): Asesorias


        data class AsesoriasEstudiante(
            override val contenido: List<AsesoriaConAsesorData>
        ): Asesorias
    }
}