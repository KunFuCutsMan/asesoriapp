package com.padieer.asesoriapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class InicioSesionUIState(
    val numeroControl: String = "",
    val contrasena: String = "" ) {
}

class InicioSesionScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(InicioSesionUIState())
    val uiState = _uiState.asStateFlow()

    private val _eventChannel = Channel<NavEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    fun onEvent(event: InicioSesionEvent) {
        when (event) {
            is InicioSesionEvent.NumControlChanged -> {
                _uiState.update { it.copy(numeroControl = event.numControl) }
            }
            is InicioSesionEvent.ContrasenaChanged -> {
                _uiState.update { it.copy(contrasena = event.contrasena) }
            }
            is InicioSesionEvent.LoginClick -> {

            }
            is InicioSesionEvent.CreaCuentaScreenClick -> {
                viewModelScope.launch { _eventChannel.send(NavEvent.CreaCuenta) }
            }
        }
    }

    fun login() {

    }

    /**
     * Eventos que debería recibir la pantalla
     */
    sealed class NavEvent {
        object CreaCuenta: NavEvent()
    }
}