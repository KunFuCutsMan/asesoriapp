package com.padieer.asesoriapp.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.padieer.asesoriapp.data.token.LoginRepository
import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.error.message
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

class InicioSesionScreenViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(InicioSesionUIState())
    val uiState = _uiState.asStateFlow()

    private val _eventChannel = Channel<Event>()
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
                viewModelScope.launch { _eventChannel.send(Event.CreaCuentaNav) }
            }
        }
    }

    private fun login() {
        val numControlResult = ValidateNumeroControlUseCase(uiState.value.numeroControl).execute()
        val contrasenaResult = ValidateContrasenaUseCase(uiState.value.contrasena).execute()

        val isValid = listOf(numControlResult, contrasenaResult).all { it is Result.Success }

        if (!isValid) {
            // Envia errores
            _uiState.update { it.copy(
                numControlError = (numControlResult as Result.Error).error.message(),
                contraError = (contrasenaResult as Result.Error).error.message()
            ) }
            return
        }

        // Envia datos al servidor
        Log.i("[SUCCESS]", "Datos enviados: ${uiState.value}")

        viewModelScope.launch {
            val result = loginRepository.getToken(
                numControl = uiState.value.numeroControl,
                contrasena = uiState.value.contrasena,
            )

            when (result) {
                is Result.Success -> {
                    // Manda a la app
                    viewModelScope.launch { _eventChannel.send(Event.AplicacionNav) }
                }
                is Result.Error -> {
                    val message = when(result.error) {
                        DataError.Network.BAD_PARAMS -> "Los parámetros son incorrectos"
                        else -> "Ocurrió un error y no sabemos que pasó :("
                    }
                    viewModelScope.launch { _eventChannel.send(Event.Toast(message)) }
                }
            }
        }
    }

    /**
     * Eventos que debería recibir la pantalla
     */
    sealed class Event {
        data object CreaCuentaNav: Event()
        data object AplicacionNav: Event()
        data class Toast(val message: String): Event()
    }
}