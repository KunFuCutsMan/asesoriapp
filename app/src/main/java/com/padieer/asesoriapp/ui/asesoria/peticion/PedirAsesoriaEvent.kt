package com.padieer.asesoriapp.ui.asesoria.peticion

import kotlinx.datetime.LocalDate

sealed class PedirAsesoriaEvent {
    data class AsignaturaChange(val asignaturaID: Int): PedirAsesoriaEvent()
    data class FechaChange(val fecha: LocalDate): PedirAsesoriaEvent()
    data class HoraInicioChange(val hora: Int): PedirAsesoriaEvent()
    data class HoraFinalChange(val hora: Int): PedirAsesoriaEvent()
    data class AsignaturaSearch(val query: String): PedirAsesoriaEvent()
    data object Submit: PedirAsesoriaEvent()
}