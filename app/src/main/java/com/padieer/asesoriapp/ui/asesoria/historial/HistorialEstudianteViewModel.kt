package com.padieer.asesoriapp.ui.asesoria.historial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.padieer.asesoriapp.App
import com.padieer.asesoriapp.data.asesoria.AsesoriaRepository
import com.padieer.asesoriapp.data.viewModelFactory
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.getters.GetLoggedInUserDataUseCase
import com.padieer.asesoriapp.domain.model.toUIModel
import com.padieer.asesoriapp.domain.nav.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistorialEstudianteViewModel(
    private val getLoggedInUserDataUseCase: GetLoggedInUserDataUseCase,
    private val asesoriaRepository: AsesoriaRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<HistorialUIState>(HistorialUIState.Loading)
    val uiState = _uiState.asStateFlow()

    val navigator = Navigator()

    init {
        viewModelScope.launch { loadInitialData() }
    }

    suspend fun loadInitialData() {
        val dataResult = getLoggedInUserDataUseCase()
        if (dataResult is Result.Error) {
            _uiState.update { HistorialUIState.Error(dataResult.error.toString()) }
            return
        }

        val (estudiante) = (dataResult as Result.Success).data

        val asesoriasResult = asesoriaRepository.fetchAsesoriasOfEstudiante(estudiante.id)
        if ( asesoriasResult is Result.Error ) {
            _uiState.update { HistorialUIState.Error(asesoriasResult.error.toString()) }
            return
        }

        val asesorias = (asesoriasResult as Result.Success).data
        val contenido = asesorias.map { it.toUIModel() }
        _uiState.update { HistorialUIState.Asesorias.AsesoriasEstudiante(contenido) }
    }

    companion object {
        fun Factory() = viewModelFactory {
            HistorialEstudianteViewModel(
                asesoriaRepository = App.appModule.asesoriaRepository,
                getLoggedInUserDataUseCase = GetLoggedInUserDataUseCase(
                    loginRepository = App.appModule.loginRepository
                ),
            )
        }
    }
}