package com.padieer.asesoriapp.ui.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class InicioSesionUIState(
    val numeroControl: String = "",
    val contrasena: String = "" ) {
}

class InicioSesionScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(InicioSesionUIState())
    val uiState = _uiState.asStateFlow()

    fun setNumeroControl(numeroControl: String) {
        _uiState.update {
            it.copy( numeroControl = numeroControl )
        }
    }

    fun setContrasena(contrasena: String) {
        _uiState.update {
            it.copy( contrasena = contrasena )
        }
    }

    fun login() {

    }
}