package com.padieer.asesoriapp.ui.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.padieer.asesoriapp.App
import com.padieer.asesoriapp.data.estudiante.EstudianteRepository
import com.padieer.asesoriapp.data.viewModelFactory
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.model.toUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PerfilAjenoViewModel(
    private val estudianteID: Int,
    private val estudianteRepository: EstudianteRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<PerfilUiState>(PerfilUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch { loadInitialData() }
    }

    suspend fun loadInitialData() {
        val estudianteRes = estudianteRepository.getEstudianteByID(estudianteID)
        when (estudianteRes) {
            is Result.Error -> {
                _uiState.update { PerfilUiState.Error(estudianteRes.error.toString()) }
            }
            is Result.Success -> {
                val estudiante = estudianteRes.data
                _uiState.update { PerfilUiState.EstudiantePerfil(
                    estudiante = estudiante.toUIModel(),
                    carrera = estudiante.carrera.toUIModel(),
                    especialidad = estudiante.especialidad?.toUIModel(),
                ) }
            }
        }
    }

    companion object {
        fun Factory(estudianteID: Int) = viewModelFactory {
            PerfilAjenoViewModel(
                estudianteRepository = App.appModule.estudianteRepository,
                estudianteID = estudianteID,
            )
        }
    }
}