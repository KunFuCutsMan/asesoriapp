package com.padieer.asesoriapp.ui.asesoria.asignarAsesor

import com.padieer.asesoriapp.domain.model.Asesoria
import com.padieer.asesoriapp.domain.model.Asesor

sealed class AsignaAsesorUIState {
    object Loading : AsignaAsesorUIState()
    data class Error(val error: String) : AsignaAsesorUIState()
    data class Success(
        val asesoria: Asesoria,
        val asesoresDisponibles: List<Asesor>
    ) : AsignaAsesorUIState()
}

// Eventos que pueden cambiar la pantalla
sealed class AsignaAsesorEvent {
    data class AsesorSeleccionado(val asesor: Asesor) : AsignaAsesorEvent()
    data class EditarAsesoria(val asesoria: Asesoria) : AsignaAsesorEvent()
}