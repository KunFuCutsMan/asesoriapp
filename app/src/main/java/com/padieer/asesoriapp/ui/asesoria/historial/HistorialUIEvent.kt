package com.padieer.asesoriapp.ui.asesoria.historial

sealed class HistorialUIEvent {

    data class ProfileClick(val estudianteID: Int): HistorialUIEvent()
}