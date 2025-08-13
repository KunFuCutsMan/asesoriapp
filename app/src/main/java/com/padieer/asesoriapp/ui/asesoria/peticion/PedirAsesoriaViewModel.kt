package com.padieer.asesoriapp.ui.asesoria.peticion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.padieer.asesoriapp.App
import com.padieer.asesoriapp.data.asesoria.AsesoriaRepository
import com.padieer.asesoriapp.data.asignatura.AsignaturaRepository
import com.padieer.asesoriapp.data.viewModelFactory
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.getters.GetLoggedInUserDataUseCase
import com.padieer.asesoriapp.domain.model.toUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class PedirAsesoriaViewModel(
    private val asesoriaRepository: AsesoriaRepository,
    private val asignaturaRepository: AsignaturaRepository,
    private val getUserDataUseCase: GetLoggedInUserDataUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow<PedirAsesoriaUIState>(PedirAsesoriaUIState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch { loadInitialData() }
    }

    @OptIn(ExperimentalTime::class)
    suspend fun loadInitialData() {
        val result = getUserDataUseCase()
        if (result is Result.Error) {
            _uiState.update { PedirAsesoriaUIState.Error(result.error.toString()) }
            return
        }

        val (_, carrera, _, _) = (result as Result.Success).data
        val asignaturasResult = asignaturaRepository.fetchAsignaturas(carrera.id)
        if (asignaturasResult is Result.Error) {
            _uiState.update { PedirAsesoriaUIState.Error(asignaturasResult.error.toString()) }
            return
        }

        val asignaturas = (asignaturasResult as Result.Success).data

        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        _uiState.update { PedirAsesoriaUIState.PedirAsesoria(
            asignaturas = asignaturas.map { it.toUIModel() },
            horaInicio = LocalTime(now.hour, 0, 0),
            horaFinal = LocalTime(now.hour + 1, 0, 0),
            dia = now.date
        ) }
    }

    private fun updateFecha(fecha: LocalDate) {
        val state = _uiState.value as PedirAsesoriaUIState.PedirAsesoria
        _uiState.update { state.copy(dia = fecha) }
    }

    private fun updateHorasInicio(horaInicio: Int) {
        val state = _uiState.value as PedirAsesoriaUIState.PedirAsesoria

        val nuevaHoraInicial = LocalTime(horaInicio, 0, 0)
        val nuevaHoraFinal = if( nuevaHoraInicial < state.horaInicio ) state.horaFinal else LocalTime(horaInicio + 1, 0,0)

        _uiState.update { state.copy(
            horaInicio = nuevaHoraInicial,
            horaFinal = nuevaHoraFinal,
        ) }
    }

    private fun updateHorasFinal(horaFinal: Int) {
        val state = _uiState.value as PedirAsesoriaUIState.PedirAsesoria

        val horaInicial = state.horaInicio
        val horaFinal = LocalTime(horaFinal, 0, 0)
        val nuevaHoraFinal = if( horaFinal > horaInicial ) horaFinal else state.horaFinal

        _uiState.update { state.copy(
            horaFinal = nuevaHoraFinal,
        ) }
    }

    private fun updateQuery(query: String) {

    }

    fun onEvent(event: PedirAsesoriaEvent) {
        when (event) {
            is PedirAsesoriaEvent.AsesoriaIndexChange -> viewModelScope.launch {  }
            is PedirAsesoriaEvent.FechaChange -> viewModelScope.launch { updateFecha(event.fecha) }
            is PedirAsesoriaEvent.HoraInicioChange -> viewModelScope.launch { updateHorasInicio(event.hora) }
            is PedirAsesoriaEvent.HoraFinalChange -> viewModelScope.launch { updateHorasFinal(event.hora) }
            is PedirAsesoriaEvent.AsignaturaSearch -> viewModelScope.launch {updateQuery(event.query)}
            PedirAsesoriaEvent.Submit -> viewModelScope.launch {  }
        }
    }

    companion object {
        fun factory() = viewModelFactory {
            PedirAsesoriaViewModel(
                asesoriaRepository = App.appModule.asesoriaRepository,
                asignaturaRepository = App.appModule.asignaturaRepository,
                getUserDataUseCase = GetLoggedInUserDataUseCase(
                    loginRepository = App.appModule.loginRepository
                )
            )
        }

        val SEARCH_DELAY = 500L
    }
}