package com.padieer.asesoriapp.ui.asesoria.historial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.padieer.asesoriapp.App
import com.padieer.asesoriapp.data.asesoria.AsesoriaRepository
import com.padieer.asesoriapp.data.viewModelFactory
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.getters.AsesoriaConAsesorData
import com.padieer.asesoriapp.domain.getters.GetAsesoriasConAsesoresDataUseCase
import com.padieer.asesoriapp.domain.getters.GetLoggedInUserDataUseCase
import com.padieer.asesoriapp.domain.nav.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistorialEstudianteViewModel(
    private val getLoggedInUserDataUseCase: GetLoggedInUserDataUseCase,
    private val getAsesoriasConAsesoresDataUseCase: GetAsesoriasConAsesoresDataUseCase,
    private val asesoriaRepository: AsesoriaRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<HistorialUIState>(HistorialUIState.Loading)
    val uiState = _uiState.asStateFlow()

    val navigator = Navigator()

    var asesoriasDelEstudiante: List<AsesoriaConAsesorData> = emptyList()

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

        asesoriasDelEstudiante = getAsesoriasConAsesoresDataUseCase(estudiante.id)
        if (asesoriasDelEstudiante.isEmpty()) {
            _uiState.update { HistorialUIState.Asesorias.NoContent() }
        }
        else {
            _uiState.update { HistorialUIState.Asesorias.AsesoriasEstudiante(asesoriasDelEstudiante) }
        }
    }

    private suspend fun muestraPerfilDeEstudiante(estudianteID: Int) {
        navigator.emit(Navigator.Action.Toast("Mostrando el perfil de estudiante $estudianteID"))
    }

    fun onEvent(event: HistorialUIEvent) {
        when (event) {
            is HistorialUIEvent.ProfileClick -> viewModelScope.launch {
                muestraPerfilDeEstudiante(event.estudianteID)
            }
        }
    }

    companion object {
        fun Factory() = viewModelFactory {
            HistorialEstudianteViewModel(
                asesoriaRepository = App.appModule.asesoriaRepository,
                getAsesoriasConAsesoresDataUseCase = GetAsesoriasConAsesoresDataUseCase(
                    estudianteRepository = App.appModule.estudianteRepository,
                    asesoriaRepository = App.appModule.asesoriaRepository,
                ),
                getLoggedInUserDataUseCase = GetLoggedInUserDataUseCase(
                    loginRepository = App.appModule.loginRepository
                ),
            )
        }
    }
}