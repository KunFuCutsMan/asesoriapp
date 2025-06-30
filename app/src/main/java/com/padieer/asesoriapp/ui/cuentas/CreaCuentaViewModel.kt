package com.padieer.asesoriapp.ui.cuentas

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CreaCuentaUIState(
    val nombre: String = "",
    val apePaterno: String = "",
    val apeMaterno: String = "",
    val numControl: String = "",
    val numTelefono: String = "",
    val numSemestre: Int = 1,
    val carrera: String = "",
    val contrasena: String = "",
    val contrasenaRepite: String = "",
)

class CreaCuentaViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CreaCuentaUIState())
    val uiState = _uiState.asStateFlow()

    fun setNombre(nom: String) = _uiState.update { it.copy( nombre = nom ) }
    fun setApePaterno(ape: String) = _uiState.update { it.copy( apePaterno = ape ) }
    fun setApeMaterno(ape: String) = _uiState.update { it.copy( apeMaterno = ape ) }
    fun setNumeroControl(num: String) = _uiState.update { it.copy( numControl = num ) }
    fun setNumeroTelefono(num: String) = _uiState.update { it.copy( numTelefono = num ) }
    fun setSemestre(num: Int) = _uiState.update { it.copy( numSemestre = num ) }
    fun setCarrera(car: String) = _uiState.update { it.copy( carrera = car ) }
    fun setContrasena(con: String) = _uiState.update { it.copy( contrasena = con ) }
    fun setContrasenaRepite(con: String) = _uiState.update { it.copy( contrasenaRepite = con ) }
}