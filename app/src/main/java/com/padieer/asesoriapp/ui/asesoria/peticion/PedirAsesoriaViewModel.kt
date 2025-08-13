package com.padieer.asesoriapp.ui.asesoria.peticion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.padieer.asesoriapp.App
import com.padieer.asesoriapp.data.asesoria.AsesoriaRepository
import com.padieer.asesoriapp.data.asignatura.AsignaturaRepository
import com.padieer.asesoriapp.data.viewModelFactory
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.getters.GetLoggedInUserDataUseCase
import com.padieer.asesoriapp.domain.model.SearchableAsignatura
import com.padieer.asesoriapp.domain.model.toSearchable
import com.padieer.asesoriapp.domain.model.toUIModel
import com.padieer.asesoriapp.domain.nav.Navigator
import com.padieer.asesoriapp.domain.search.Searcher
import com.padieer.asesoriapp.domain.validators.ValidateAsignaturaUseCase
import com.padieer.asesoriapp.domain.validators.ValidateHoraFinalUseCase
import com.padieer.asesoriapp.domain.validators.ValidateHoraInicioUseCase
import com.padieer.asesoriapp.ui.nav.AppScreen
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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
    private val asignaturaSearcher: Searcher<SearchableAsignatura>,
    private val getUserDataUseCase: GetLoggedInUserDataUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow<PedirAsesoriaUIState>(PedirAsesoriaUIState.Loading)
    val uiState = _uiState.asStateFlow()

    private var carreraID: Int? = null

    private var asignaturaQueryJob: Job? = null

    val navitator = Navigator()

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
        this.carreraID = carrera.id
        val asignaturasResult = asignaturaRepository.fetchAsignaturas(carrera.id)
        if (asignaturasResult is Result.Error) {
            _uiState.update { PedirAsesoriaUIState.Error(asignaturasResult.error.toString()) }
            return
        }

        val asignaturas = (asignaturasResult as Result.Success).data

        // Inicializa el buscador
        asignaturaSearcher.init()
        asignaturaSearcher.put(asignaturas.map { it.toSearchable() })

        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        var nowHour = now.hour
        if (nowHour < 7) nowHour = 7
        if (nowHour > 20) nowHour = 20

        _uiState.update { PedirAsesoriaUIState.PedirAsesoria(
            asignaturas = asignaturas.map { it.toUIModel() },
            horaInicio = LocalTime(nowHour, 0, 0),
            horaFinal = LocalTime(nowHour + 1, 0, 0),
            dia = now.date
        ) }
    }

    private fun updateFecha(fecha: LocalDate) {
        val state = _uiState.value as PedirAsesoriaUIState.PedirAsesoria
        _uiState.update { state.copy(dia = fecha) }
    }

    private fun updateHorasInicio(hora: Int) {
        val state = _uiState.value as PedirAsesoriaUIState.PedirAsesoria

        var horaInicio = hora
        if (horaInicio < 7) horaInicio = 7
        if (horaInicio > 20) horaInicio = 20

        val nuevaHoraInicial = LocalTime(horaInicio, 0, 0)
        val nuevaHoraFinal = if( nuevaHoraInicial < state.horaInicio ) state.horaFinal else LocalTime(horaInicio + 1, 0,0)

        _uiState.update { state.copy(
            horaInicio = nuevaHoraInicial,
            horaFinal = nuevaHoraFinal,
        ) }
    }

    private fun updateHorasFinal(hora: Int) {
        val state = _uiState.value as PedirAsesoriaUIState.PedirAsesoria

        var horaFinal = hora
        if (horaFinal < 8) horaFinal = 8
        if (horaFinal > 21) horaFinal = 21

        val horaInicial = state.horaInicio
        val horarioFinal = LocalTime(horaFinal, 0, 0)
        val nuevaHoraFinal = if( horarioFinal > horaInicial ) horarioFinal else state.horaFinal

        _uiState.update { state.copy(
            horaFinal = nuevaHoraFinal,
        ) }
    }

    private fun updateQuery(query: String) {
        val state = _uiState.value as PedirAsesoriaUIState.PedirAsesoria
        _uiState.update { state.copy(asignaturaQuery = query) }

        asignaturaQueryJob?.cancel()
        asignaturaQueryJob = viewModelScope.launch {
            delay(SEARCH_DELAY) // Espera un momento bro

            val asignaturas = asignaturaSearcher
                .query(query)
                .map { it.toUIModel() }

            val queriedState = _uiState.value as PedirAsesoriaUIState.PedirAsesoria
            _uiState.update { queriedState.copy(
                asignaturas = asignaturas
            )}
        }
    }

    private fun updateAsignatura(asignaturaID: Int) {
        val state = _uiState.value as PedirAsesoriaUIState.PedirAsesoria
        _uiState.update { state.copy(asignaturaID = asignaturaID) }
    }

    private suspend fun postAsesoria() {
        val state = _uiState.value as PedirAsesoriaUIState.PedirAsesoria
        _uiState.update { state.copy(isValidating = true, errors = null) }

        val horaInicioValidation = ValidateHoraInicioUseCase(state.horaInicio).execute()
        val horaFinalValidation = ValidateHoraFinalUseCase(state.horaInicio, state.horaFinal).execute()
        val asignaturaValidation = ValidateAsignaturaUseCase(state.asignaturaID, asignaturaRepository).execute()
        val validations = listOf(horaInicioValidation, horaFinalValidation, asignaturaValidation)
        val isValid = validations.all { it is Result.Success }



        if (isValid) {
            _uiState.update { state.copy(isValidating = false, errors = null) }
            viewModelScope.launch { creaAsesoria( state.asignaturaID, state.dia, state.horaInicio, state.horaFinal ) }
        }
        else {
            val errors = validations.mapNotNull { if (it is Result.Error) it.error.message else null }
            _uiState.update { state.copy( isValidating = false, errors = errors ) }
        }
    }

    private suspend fun creaAsesoria(
        asignaturaID: Int, diaAsesoria: LocalDate, horaInicial: LocalTime, horaFinal: LocalTime
    ) {
        val asesoriaResult = asesoriaRepository.postAsesoria(
            carreraID = this.carreraID!!,
            asignaturaID = asignaturaID,
            fecha = diaAsesoria,
            horaInicio = horaInicial,
            horaFinal = horaFinal,
        )

        if (asesoriaResult is Result.Error) {
            val state = _uiState.value as PedirAsesoriaUIState.PedirAsesoria
            _uiState.update { state.copy(isValidating = false, errors = listOf(asesoriaResult.error.toString())) }
            return
        }
        else {
            this.navitator.emit(Navigator.Action.Toast("Se creó la asesoría"))
            delay(3000L)
            this.navitator.emit(Navigator.Action.GoTo(AppScreen.Asesoria.HistorialAsesoriasScreen))
        }
    }

    fun onEvent(event: PedirAsesoriaEvent) {
        when (event) {
            is PedirAsesoriaEvent.AsignaturaChange -> viewModelScope.launch { updateAsignatura(event.asignaturaID) }
            is PedirAsesoriaEvent.FechaChange -> viewModelScope.launch { updateFecha(event.fecha) }
            is PedirAsesoriaEvent.HoraInicioChange -> viewModelScope.launch { updateHorasInicio(event.hora) }
            is PedirAsesoriaEvent.HoraFinalChange -> viewModelScope.launch { updateHorasFinal(event.hora) }
            is PedirAsesoriaEvent.AsignaturaSearch -> viewModelScope.launch {updateQuery(event.query)}
            PedirAsesoriaEvent.Submit -> viewModelScope.launch { postAsesoria() }
        }
    }

    companion object {
        fun factory() = viewModelFactory {
            PedirAsesoriaViewModel(
                asesoriaRepository = App.appModule.asesoriaRepository,
                asignaturaRepository = App.appModule.asignaturaRepository,
                getUserDataUseCase = GetLoggedInUserDataUseCase(
                    loginRepository = App.appModule.loginRepository
                ),
                asignaturaSearcher = App.appModule.asignaturaSearcher
            )
        }

        const val SEARCH_DELAY = 1000L
    }
}