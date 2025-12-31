package com.padieer.asesoriapp.ui.asesoria.terminar

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TerminarAsesoriaViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TerminarAsesoriaUIState())
    val uiState: StateFlow<TerminarAsesoriaUIState> = _uiState.asStateFlow()

    fun onEvent(event: TerminarAsesoriaEvent) {
        when (event) {
            is TerminarAsesoriaEvent.OnCodigoAsesorChanged -> {
                _uiState.update { it.copy(codigoAsesor = event.codigo) }
            }
            is TerminarAsesoriaEvent.OnCodigoEstudianteChanged -> {
                _uiState.update { it.copy(codigoEstudiante = event.codigo) }
            }
            TerminarAsesoriaEvent.OnSubmit -> {
                // TODO: Lógica para finalizar la asesoria
            }
        }
    }
}