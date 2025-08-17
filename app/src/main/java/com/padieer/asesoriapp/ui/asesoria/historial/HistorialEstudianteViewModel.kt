package com.padieer.asesoriapp.ui.asesoria.historial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.padieer.asesoriapp.App
import com.padieer.asesoriapp.data.viewModelFactory
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.getters.AsesoriaConAsesorData
import com.padieer.asesoriapp.domain.getters.GetAsesoriasConAsesoresDataUseCase
import com.padieer.asesoriapp.domain.getters.GetLoggedInUserDataUseCase
import com.padieer.asesoriapp.domain.nav.Navigator
import com.padieer.asesoriapp.ui.nav.AppScreen
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class HistorialEstudianteViewModel(
    private val getLoggedInUserDataUseCase: GetLoggedInUserDataUseCase,
    private val getAsesoriasConAsesoresDataUseCase: GetAsesoriasConAsesoresDataUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow<HistorialUIState>(HistorialUIState.Loading)
    val uiState = _uiState.asStateFlow()

    val navigator = Navigator()

    var asesoriasDelEstudiante: List<AsesoriaConAsesorData> = emptyList()
    var filtrarJob: Job? = null

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

        asesoriasDelEstudiante = getAsesoriasConAsesoresDataUseCase(estudiante.id)
        if (asesoriasDelEstudiante.isEmpty()) {
            _uiState.update { HistorialUIState.Asesorias.NoContent() }
        }
        else {
            _uiState.update { HistorialUIState.Asesorias.AsesoriasEstudiante(asesoriasDelEstudiante) }
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun filtrarAsesorias() {
        val estado = _uiState.value as HistorialUIState.Asesorias
        val filtroTiempo = estado.tiempoFilter
        val filtroEstado = estado.estadosFilter
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

        val asesoriasMostradas = asesoriasDelEstudiante
            .filter { (asesoria, _, _) -> when (filtroTiempo) {
                TiempoFilter.ALL -> true
                TiempoFilter.PASADAS -> asesoria.dia < now.date && asesoria.horaInicial < now.time && asesoria.horaFinal < now.time
                TiempoFilter.FUTURAS -> asesoria.dia > now.date
                TiempoFilter.HOY -> asesoria.dia == now.date
            } }
            .filter { (_, _, estadoAsesoria) -> when (filtroEstado) {
                EstadoFilter.ALL -> true
                EstadoFilter.PENDIENTE -> estadoAsesoria.id == 1
                EstadoFilter.EN_PROCESO -> estadoAsesoria.id == 2
                EstadoFilter.COMPLETADA -> estadoAsesoria.id == 3
                EstadoFilter.CANCELADA -> estadoAsesoria.id == 4
            } }
            .sortedBy { (asesoria, _, _) -> asesoria.dia }
            .sortedBy { (asesoria, _, _) -> asesoria.horaInicial }

        if (asesoriasMostradas.isEmpty()) {
            _uiState.update { HistorialUIState.Asesorias.NoContent(
                tiempoFilter = filtroTiempo,
                estadosFilter = filtroEstado
            )}
        }
        else {
            _uiState.update { HistorialUIState.Asesorias.AsesoriasEstudiante(
                contenido = asesoriasMostradas,
                tiempoFilter = filtroTiempo,
                estadosFilter = filtroEstado,
            ) }
        }
    }

    private suspend fun muestraPerfilDeEstudiante(estudianteID: Int) {
        navigator.emit(Navigator.Action.GoTo(AppScreen.Usuario.PerfilAjeno(estudianteID)))
    }

    private fun cambiaFiltroEstado(estado: EstadoFilter) {
        val state = _uiState.value as HistorialUIState.Asesorias
        val nuevoEstado = if (estado == state.estadosFilter) EstadoFilter.ALL else estado
        when (state) {
            is HistorialUIState.Asesorias.AsesoriasEstudiante -> _uiState.update { state.copy(estadosFilter = nuevoEstado) }
            is HistorialUIState.Asesorias.NoContent -> _uiState.update { state.copy(estadosFilter = nuevoEstado) }
        }

        filtrarJob?.cancel()
        filtrarJob = viewModelScope.launch { filtrarAsesorias() }
    }

    private fun cambiaFiltroTiempo(tiempo: TiempoFilter) {
        val state = _uiState.value as HistorialUIState.Asesorias
        when (state) {
            is HistorialUIState.Asesorias.AsesoriasEstudiante -> _uiState.update { state.copy(tiempoFilter = tiempo) }
            is HistorialUIState.Asesorias.NoContent -> _uiState.update { state.copy(tiempoFilter = tiempo) }
        }

        filtrarJob?.cancel()
        filtrarJob = viewModelScope.launch { filtrarAsesorias() }
    }

    fun onEvent(event: HistorialUIEvent) {
        when (event) {
            is HistorialUIEvent.ProfileClick -> viewModelScope.launch {
                muestraPerfilDeEstudiante(event.estudianteID)
            }

            is HistorialUIEvent.EstadoFilterChange -> viewModelScope.launch {
                cambiaFiltroEstado(event.estado)
            }
            is HistorialUIEvent.TiempoFilterChange -> viewModelScope.launch {
                cambiaFiltroTiempo(event.tiempo)
            }
        }
    }

    companion object {
        fun Factory() = viewModelFactory {
            HistorialEstudianteViewModel(
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