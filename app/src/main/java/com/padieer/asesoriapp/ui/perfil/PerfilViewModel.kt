package com.padieer.asesoriapp.ui.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.padieer.asesoriapp.App
import com.padieer.asesoriapp.data.viewModelFactory
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.getters.GetLoggedInUserDataUseCase
import com.padieer.asesoriapp.ui.common.toUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PerfilViewModel(
    private val userDataUseCase: GetLoggedInUserDataUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow<PerfilUiState>(PerfilUiState.Loading)
    val state = _uiState.asStateFlow()

    init {
        viewModelScope.launch { loadInitialData() }
    }

    suspend fun loadInitialData() {
        when (val res = userDataUseCase()) {
            is Result.Error -> {
                _uiState.update { PerfilUiState.Error(res.error.toString()) }
            }
            is Result.Success -> {
                val (estudiante, carrera) = res.data
                _uiState.update { PerfilUiState.EstudiantePerfil(
                    estudiante = estudiante.toUIModel(),
                    carrera = carrera.toUIModel(),
                ) }
            }
        }
    }

    companion object {
        fun Factory() = viewModelFactory {
            PerfilViewModel(
                userDataUseCase = GetLoggedInUserDataUseCase(
                    loginRepository = App.appModule.loginRepository,
                    carreraRepository = App.appModule.carreraRepository
                )
            )
        }
    }
}