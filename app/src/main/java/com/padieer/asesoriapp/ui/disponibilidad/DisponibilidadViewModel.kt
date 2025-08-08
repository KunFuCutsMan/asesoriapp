package com.padieer.asesoriapp.ui.disponibilidad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.padieer.asesoriapp.App
import com.padieer.asesoriapp.data.horario.HorarioRepository
import com.padieer.asesoriapp.data.viewModelFactory
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.getters.GetLoggedInUserDataUseCase
import com.padieer.asesoriapp.domain.model.AsesorModel
import com.padieer.asesoriapp.domain.model.HorarioModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalTime

class DisponibilidadViewModel(
    private val horarioRepository: HorarioRepository,
    private val getUserDataUseCase: GetLoggedInUserDataUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<DisponibilidadUIState>(DisponibilidadUIState.Loading)
    val uiState = _uiState.asStateFlow()

    private var asesor: AsesorModel? = null

    init {
        viewModelScope.launch {
            loadData()
        }
    }

    suspend fun loadData(): Unit {
        val estudianteRes = getUserDataUseCase()
        if (estudianteRes is Result.Error) {
            _uiState.update { DisponibilidadUIState.Error(estudianteRes.error.toString()) }
            return
        }

        val (_, _, asesor) = (estudianteRes as Result.Success).data
        if (asesor == null) {
            _uiState.update { DisponibilidadUIState.Error("No eres un asesor que haces aqui") }
            return
        }
        this.asesor = asesor

        val horariosResult = horarioRepository.fetchHorarios(asesor.id)
        when (horariosResult) {
            is Result.Error -> {
                _uiState.update { DisponibilidadUIState.Error(horariosResult.error.toString()) }
            }
            is Result.Success -> {
                parseHorariosToState(horariosResult.data)
            }
        }
    }

    private fun parseHorariosToState(horarios: List<HorarioModel>) {
        val horarios = (1..5).map { diaSemanaID ->
            val horariosOcupados = horarios.filter { it.diaSemana.id == diaSemanaID && !it.disponible }
            return@map generaHoras(horariosOcupados)
        }

        _uiState.update { DisponibilidadUIState.Disponibilidad(
            lunes = horarios[0],
            martes = horarios[1],
            miercoles = horarios[2],
            jueves = horarios[3],
            viernes = horarios[4]
        ) }
    }

    private fun generaHoras(horariosOcupados: List<HorarioModel>): List<Hora> {
        return (7..20).map { hora ->
            val horario = horariosOcupados.firstOrNull { horario -> horario.horaInicio.hour == hora }
            return@map horario?.toHora() ?: Hora(hora, false)
        }
    }

    private suspend fun editaHorarioClick() {
        if (asesor == null)
            return

        val state = _uiState.value as DisponibilidadUIState.Disponibilidad

        val horariosModificados = emptyList<HorarioRepository.HorarioParams>()
            .plus(state.lunes.map { it.toHorarioParams(1) })
            .plus(state.martes.map { it.toHorarioParams(2) })
            .plus(state.miercoles.map { it.toHorarioParams(3) })
            .plus(state.jueves.map { it.toHorarioParams(4) })
            .plus(state.viernes.map { it.toHorarioParams(5) })

        val result = horarioRepository.updateHorarios(
            asesorID = asesor!!.id,
            horarios = horariosModificados
        )

        when (result) {
            is Result.Success -> viewModelScope.launch {
                parseHorariosToState(result.data)
            }
            is Result.Error -> viewModelScope.launch {
                _uiState.update { DisponibilidadUIState.Error(result.error.toString()) }
            }
        }
    }

    private fun toggleHoraLunes(index: Int) {
        val state = _uiState.value as DisponibilidadUIState.Disponibilidad
        val nuevasHoras = state.lunes.mapIndexed { i, oldHora ->
            if (i == index) oldHora.copy( ocupado = !oldHora.ocupado ) else oldHora
        }
        _uiState.update { state.copy( lunes = nuevasHoras ) }
    }

    private fun toggleHoraMartes(index: Int) {
        val state = _uiState.value as DisponibilidadUIState.Disponibilidad
        val nuevasHoras = state.martes.mapIndexed { i, oldHora ->
            if (i == index) oldHora.copy( ocupado = !oldHora.ocupado ) else oldHora
        }
        _uiState.update { state.copy( martes = nuevasHoras ) }
    }

    private fun toggleHoraMiercoles(index: Int) {
        val state = _uiState.value as DisponibilidadUIState.Disponibilidad
        val nuevasHoras = state.miercoles.mapIndexed { i, oldHora ->
            if (i == index) oldHora.copy( ocupado = !oldHora.ocupado ) else oldHora
        }
        _uiState.update { state.copy( miercoles = nuevasHoras ) }
    }

    private fun toggleHoraJueves(index: Int) {
        val state = _uiState.value as DisponibilidadUIState.Disponibilidad
        val nuevasHoras = state.jueves.mapIndexed { i, oldHora ->
            if (i == index) oldHora.copy( ocupado = !oldHora.ocupado ) else oldHora
        }
        _uiState.update { state.copy( jueves = nuevasHoras ) }
    }

    private fun toggleHoraViernes(index: Int) {
        val state = _uiState.value as DisponibilidadUIState.Disponibilidad
        val nuevasHoras = state.viernes.mapIndexed { i, oldHora ->
            if (i == index) oldHora.copy( ocupado = !oldHora.ocupado ) else oldHora
        }
        _uiState.update { state.copy( viernes = nuevasHoras ) }
    }

    fun onEvent(event: DisponibilidadEvent) {
        when (event) {
            is DisponibilidadEvent.HoraLunesClick -> viewModelScope.launch {
                toggleHoraLunes(event.index)
            }
            is DisponibilidadEvent.HoraMartesClick -> viewModelScope.launch {
                toggleHoraMartes(event.index)
            }
            is DisponibilidadEvent.HoraMiercolesClick -> viewModelScope.launch {
                toggleHoraMiercoles(event.index)
            }
            is DisponibilidadEvent.HoraJuevesClick -> viewModelScope.launch {
                toggleHoraJueves(event.index)
            }
            is DisponibilidadEvent.HoraViernesClick -> viewModelScope.launch{
                toggleHoraViernes(event.index)
            }
            DisponibilidadEvent.EditaDisponibilidadClick -> viewModelScope.launch {
                editaHorarioClick()
            }
        }
    }

    companion object {
        fun Factory() = viewModelFactory {
            DisponibilidadViewModel(
                horarioRepository = App.appModule.horarioRepository,
                getUserDataUseCase = GetLoggedInUserDataUseCase(
                    loginRepository = App.appModule.loginRepository
                )
            )
        }
    }
}

private fun HorarioModel.toHora() = Hora(
    hora = this.horaInicio.hour,
    ocupado = !this.disponible
)

private fun Hora.toHorarioParams(diaSemanaID: Int) = HorarioRepository.HorarioParams(
    horaInicio = LocalTime(this.hora, 0, 0, 0),
    disponible = !this.ocupado,
    diaSemanaID = diaSemanaID,
)