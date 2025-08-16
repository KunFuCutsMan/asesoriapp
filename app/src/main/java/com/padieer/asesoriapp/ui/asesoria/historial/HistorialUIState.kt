package com.padieer.asesoriapp.ui.asesoria.historial

import com.padieer.asesoriapp.domain.model.Asesoria

sealed interface HistorialUIState {

    data object Loading: HistorialUIState

    data class Error(
        val error: String,
    ): HistorialUIState

    sealed interface Asesorias: HistorialUIState {

        val contenido: List<Asesoria>

        data class NoContent(
            override val contenido: List<Asesoria> = emptyList()
        ): Asesorias


        data class AsesoriasEstudiante(
            override val contenido: List<Asesoria>
        ): Asesorias
    }
}