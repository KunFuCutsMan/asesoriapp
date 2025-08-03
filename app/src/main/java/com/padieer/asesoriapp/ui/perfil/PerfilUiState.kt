package com.padieer.asesoriapp.ui.perfil

import com.padieer.asesoriapp.ui.common.Carrera
import com.padieer.asesoriapp.ui.common.Estudiante

sealed class PerfilUiState {

    data object Loading: PerfilUiState()

    data class Error(val error: String): PerfilUiState()

    data class EstudiantePerfil(val estudiante: Estudiante, val carrera: Carrera): PerfilUiState()

}