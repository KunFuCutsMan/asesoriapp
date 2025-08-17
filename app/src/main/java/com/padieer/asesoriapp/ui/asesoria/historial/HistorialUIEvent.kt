package com.padieer.asesoriapp.ui.asesoria.historial

sealed class HistorialUIEvent {

    data class ProfileClick(val estudianteID: Int): HistorialUIEvent()

    data class EstadoFilterChange(val estado: EstadoFilter): HistorialUIEvent()

    data class TiempoFilterChange(val tiempo: TiempoFilter): HistorialUIEvent()
}