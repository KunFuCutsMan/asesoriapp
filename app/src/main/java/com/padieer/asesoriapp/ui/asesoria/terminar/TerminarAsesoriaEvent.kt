package com.padieer.asesoriapp.ui.asesoria.terminar

sealed class TerminarAsesoriaEvent {
    data class OnCodigoAsesorChanged(val codigo: String) : TerminarAsesoriaEvent()
    data class OnCodigoEstudianteChanged(val codigo: String) : TerminarAsesoriaEvent()
    data object OnSubmit : TerminarAsesoriaEvent()
}