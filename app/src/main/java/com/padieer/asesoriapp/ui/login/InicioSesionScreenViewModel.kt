package com.padieer.asesoriapp.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.padieer.asesoriapp.App
import com.padieer.asesoriapp.data.estudiante.EstudianteRepository
import com.padieer.asesoriapp.data.token.LoginRepository
import com.padieer.asesoriapp.data.viewModelFactory
import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.nav.Navigator
import com.padieer.asesoriapp.domain.validators.ValidateContrasenaUseCase
import com.padieer.asesoriapp.domain.validators.ValidateNumeroControlUseCase
import com.padieer.asesoriapp.domain.validators.messageOrNull
import com.padieer.asesoriapp.ui.nav.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class InicioSesionUIState(
    val numeroControl: String = "",
    val contrasena: String = "",
    val numControlError: String? = null,
    val contraError: String? = null
)

class InicioSesionScreenViewModel(
    private val loginRepository: LoginRepository,
    private val estudianteRepository: EstudianteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(InicioSesionUIState())
    val uiState = _uiState.asStateFlow()

    val navigator = Navigator()

    fun onEvent(event: InicioSesionEvent) {
        when (event) {
            is InicioSesionEvent.NumControlChanged -> viewModelScope.launch {
                _uiState.update { it.copy(numeroControl = event.numControl) }
            }
            is InicioSesionEvent.ContrasenaChanged -> viewModelScope.launch {
                _uiState.update { it.copy(contrasena = event.contrasena) }
            }
            is InicioSesionEvent.LoginClick -> viewModelScope.launch { login() }
            is InicioSesionEvent.CreaCuentaScreenClick -> viewModelScope.launch {
                navigator.emit(Navigator.Action.GoTo(Screen.Auth.CreaCuentaScreen))
            }
            is InicioSesionEvent.ForgotPasswordClick -> viewModelScope.launch {
                navigator.emit(Navigator.Action.GoTo(Screen.Auth.ForgotPasswordScreen))
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
                numControlError = numControlResult.messageOrNull(),
                contraError = contrasenaResult.messageOrNull()
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

            if (result is Result.Error) {
                val message = when(result.error) {
                    DataError.Network.BAD_PARAMS -> "Los parámetros son incorrectos"
                    else -> "Ocurrió un error y no sabemos que pasó :("
                }
                viewModelScope.launch {
                    navigator.emit(Navigator.Action.Toast(message))
                }
                return@launch
            }

            val token = (result as Result.Success).data
            when (val estudianteRes = estudianteRepository.getEstudianteByToken(token)) {
                is Result.Success -> viewModelScope.launch {
                    navigator.emit(Navigator.Action.GoToInclusive(
                        screen = Screen.App,
                        upTo = Screen.Auth
                    ))
                }
                is Result.Error -> {
                    val message = estudianteRes.toString()
                    viewModelScope.launch {
                        navigator.emit(Navigator.Action.Toast(message))
                    }
                }
            }
        }
    }

    companion object {
        fun Factory() = viewModelFactory {
            InicioSesionScreenViewModel(
                loginRepository = App.appModule.loginRepository,
                estudianteRepository = App.appModule.estudianteRepository
            )
        }
    }
}