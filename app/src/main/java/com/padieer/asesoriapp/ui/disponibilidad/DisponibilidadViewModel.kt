package com.padieer.asesoriapp.ui.disponibilidad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.padieer.asesoriapp.data.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DisponibilidadViewModel: ViewModel() {

    private val _uiState = MutableStateFlow<DisponibilidadUIState>(DisponibilidadUIState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { DisponibilidadUIState.Disponibilidad(
            lunes = generaHoras(),
            martes = generaHoras(),
            miercoles = generaHoras(),
            jueves = generaHoras(),
            viernes = generaHoras(),
        ) }
    }

    private fun editaHorarioClick() {

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
            DisponibilidadViewModel()
        }

        fun generaHoras() = (7..20).map { Hora(it, false) }
    }
}