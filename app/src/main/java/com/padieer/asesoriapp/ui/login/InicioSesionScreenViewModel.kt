package com.padieer.asesoriapp.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.padieer.asesoriapp.domain.validators.ValidateContrasenaUseCase
import com.padieer.asesoriapp.domain.validators.ValidateNumeroControlUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class InicioSesionUIState(
    val numeroControl: String = "",
    val contrasena: String = "",
    val numControlError: String? = null,
    val contraError: String? = null
)

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
                // Valida datos e inicia sesión
                this.login()
            }
            is InicioSesionEvent.CreaCuentaScreenClick -> {
                viewModelScope.launch { _eventChannel.send(NavEvent.CreaCuenta) }
            }
        }
    }

    private fun login() {
        val numControlResult = ValidateNumeroControlUseCase(uiState.value.numeroControl).execute()
        val contrasenaResult = ValidateContrasenaUseCase(uiState.value.contrasena).execute()

        val isValid = listOf(numControlResult, contrasenaResult).all { it.isSuccessful }

        if (!isValid) {
            // Envia errores
            _uiState.update { it.copy(
                numControlError = numControlResult.errorMessage,
                contraError = contrasenaResult.errorMessage
            ) }
            return
        }

        // Envia datos al servidor
        Log.i("[SUCCESS]", "Datos enviados: ${uiState.value}")
    }

    /**
     * Eventos que debería recibir la pantalla
     */
    sealed class NavEvent {
        data object CreaCuenta: NavEvent()
    }
}