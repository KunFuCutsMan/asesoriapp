package com.padieer.asesoriapp.ui.perfil

sealed class PerfilUiState {

    data object Loading: PerfilUiState()

    data class Error(val error: String): PerfilUiState()

    data class EstudiantePerfil(val estudiante: Estudiante, val carrera: Carrera): PerfilUiState()

}