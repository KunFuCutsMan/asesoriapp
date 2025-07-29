package com.padieer.asesoriapp.ui.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.padieer.asesoriapp.App
import com.padieer.asesoriapp.data.token.LoginRepository
import com.padieer.asesoriapp.data.viewModelFactory
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.nav.Navigator
import com.padieer.asesoriapp.ui.nav.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UIState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val rolEstudiante: RolEstudiante = RolEstudiante.ESTUDIANTE
)

class AppViewModel(
    private val loginRepository: LoginRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(UIState())
    val uiState = _uiState.asStateFlow()

    val navigator = Navigator()

    init {
        viewModelScope.launch {
            val estudianteRes = loginRepository.getLoggedInUser()

            if (estudianteRes is Result.Error) {
                _uiState.update { it.copy(isLoading = false, error = estudianteRes.error.toString()) }
                    return@launch
            }

            val estudiante = (estudianteRes as Result.Success).data

            estudiante.asesor?.let { _uiState.update { it.copy(rolEstudiante = RolEstudiante.ASESOR) } }
            estudiante.asesor?.admin?.let { _uiState.update { it.copy(rolEstudiante = RolEstudiante.ADMIN) } }

            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private suspend fun logOut() {
        when (val logOutResult = loginRepository.logOut()) {
            is Result.Success -> {
                navigator.emit(Navigator.Action.GoToInclusive(screen = Screen.Auth, upTo = Screen.App))
            }
            is Result.Error -> {
                navigator.emit(Navigator.Action.Toast(logOutResult.error.toString()))
            }
        }
    }

    fun onEvent(event: AppEvent) {
        when (event) {
            AppEvent.LogOutClick -> { viewModelScope.launch { logOut() } }
        }
    }

    companion object {
        fun Factory() = viewModelFactory {
            AppViewModel(
                loginRepository = App.appModule.loginRepository
            )
        }
    }
}