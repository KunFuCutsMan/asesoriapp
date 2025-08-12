package com.padieer.asesoriapp.ui.asesoria.peticion

import kotlinx.datetime.LocalDate

sealed class PedirAsesoriaEvent {
    data class AsesoriaIndexChange(val index: Int): PedirAsesoriaEvent()
    data class FechaChange(val fecha: LocalDate): PedirAsesoriaEvent()
    data class HoraInicioChange(val hora: Int): PedirAsesoriaEvent()
    data class HoraFinalChange(val hora: Int): PedirAsesoriaEvent()
    data object Submit: PedirAsesoriaEvent()
}