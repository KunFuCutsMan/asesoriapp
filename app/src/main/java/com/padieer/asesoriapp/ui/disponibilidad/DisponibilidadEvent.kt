package com.padieer.asesoriapp.ui.disponibilidad

sealed class DisponibilidadEvent {
    data class HoraLunesClick(val index: Int): DisponibilidadEvent()
    data class HoraMartesClick(val index: Int): DisponibilidadEvent()
    data class HoraMiercolesClick(val index: Int): DisponibilidadEvent()
    data class HoraJuevesClick(val index: Int): DisponibilidadEvent()
    data class HoraViernesClick(val index: Int): DisponibilidadEvent()

    data object EditaDisponibilidadClick: DisponibilidadEvent()
}