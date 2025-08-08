package com.padieer.asesoriapp.ui.disponibilidad

sealed class DisponibilidadUIState {

    object Loading: DisponibilidadUIState()

    data class Error(val error: String): DisponibilidadUIState()

    data class Disponibilidad(
        val lunes: List<Hora>,
        val martes: List<Hora>,
        val miercoles: List<Hora>,
        val jueves: List<Hora>,
        val viernes: List<Hora>,
        val loading: Boolean = false,
        val error: String? = null,
    ): DisponibilidadUIState()
}