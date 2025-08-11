package com.padieer.asesoriapp.ui.asesoria.peticion

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

sealed class PedirAsesoriaUIState {

    data object Loading: PedirAsesoriaUIState()

    data class PedirAsesoria(
        val carreraID: Int,
        val asignaturaID: Int,
        val horaInicio: LocalTime,
        val horaFinal: LocalTime,
        val dia: LocalDate,
    ): PedirAsesoriaUIState()
}