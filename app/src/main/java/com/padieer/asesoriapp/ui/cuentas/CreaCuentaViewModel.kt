package com.padieer.asesoriapp.ui.cuentas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.padieer.asesoriapp.data.carrera.CarreraModel
import com.padieer.asesoriapp.data.carrera.CarreraRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CreaCuentaUIState(
    val isLoading: Boolean = false,
    val carrerasList: List<Carrera> = listOf(Carrera(""))
)

data class FormData(
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

class CreaCuentaViewModel(
    private val carreraRepository: CarreraRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreaCuentaUIState())
    val uiState = _uiState.asStateFlow()

    private val _formDataState = MutableStateFlow(FormData())
    val formDataState = _formDataState.asStateFlow()

    init {
        getInitialData()
    }

    private fun getInitialData() {
        _uiState.update { it.copy( isLoading = true ) }

        viewModelScope.launch {
            val carreras = carreraRepository.getCarreras()
            _uiState.update { state ->
                state.copy(
                    carrerasList = carreras.map { it.toUIModel() },
                    isLoading = false
                )
            }

        }
    }

    fun onCreaCuentaClick() {
        //
    }
    fun onIniciaSesionClick() {
        //
    }

    fun setNombre(nom: String) = _formDataState.update { it.copy( nom ) }
    fun setApePaterno(ape: String) = _formDataState.update { it.copy( apePaterno = ape ) }
    fun setApeMaterno(ape: String) = _formDataState.update { it.copy( apeMaterno = ape ) }
    fun setNumeroControl(num: String) = _formDataState.update { it.copy( numControl = num ) }
    fun setNumeroTelefono(num: String) = _formDataState.update { it.copy( numTelefono = num ) }
    fun setSemestre(num: Int) = _formDataState.update { it.copy( numSemestre = num ) }
    fun setCarrera(car: String) = _formDataState.update { it.copy( carrera = car ) }
    fun setContrasena(con: String) = _formDataState.update { it.copy( contrasena = con ) }
    fun setContrasenaRepite(con: String) = _formDataState.update { it.copy( contrasenaRepite = con ) }
}

fun CarreraModel.toUIModel(): Carrera {
    return Carrera(
        nombre = this.nombre
    )
}

data class Carrera(
    val nombre: String
)