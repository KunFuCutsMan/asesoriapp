package com.padieer.asesoriapp.ui.perfil

import com.padieer.asesoriapp.domain.model.Carrera
import com.padieer.asesoriapp.domain.model.Especialidad
import com.padieer.asesoriapp.domain.model.Estudiante

sealed class PerfilUiState {

    data object Loading: PerfilUiState()

    data class Error(val error: String): PerfilUiState()

    data class EstudiantePerfil(
        val estudiante: Estudiante,
        val carrera: Carrera,
        val especialidad: Especialidad?
        ): PerfilUiState()

}