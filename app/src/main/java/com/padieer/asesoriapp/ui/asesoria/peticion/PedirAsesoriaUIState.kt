package com.padieer.asesoriapp.ui.asesoria.peticion

import com.padieer.asesoriapp.domain.model.Asignatura
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

sealed class PedirAsesoriaUIState {

    data object Loading: PedirAsesoriaUIState()
    data class Error(val error: String): PedirAsesoriaUIState()

    data class PedirAsesoria(
        val asignaturas: List<Asignatura>,
        val asignaturaQuery: String = "",
        val horaInicio: LocalTime,
        val horaFinal: LocalTime,
        val dia: LocalDate,
        val asignaturaID: Int = 0,
        val isValidating: Boolean = false,
        val errors: List<String>? = null
    ): PedirAsesoriaUIState()
}