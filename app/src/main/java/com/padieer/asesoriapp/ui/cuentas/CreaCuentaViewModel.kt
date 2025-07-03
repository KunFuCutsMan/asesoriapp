package com.padieer.asesoriapp.ui.cuentas

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.padieer.asesoriapp.data.carrera.CarreraModel
import com.padieer.asesoriapp.data.carrera.CarreraRepository
import com.padieer.asesoriapp.domain.validators.ValidateApellidoUseCase
import com.padieer.asesoriapp.domain.validators.ValidateCarreraUseCase
import com.padieer.asesoriapp.domain.validators.ValidateContraRepiteUseCase
import com.padieer.asesoriapp.domain.validators.ValidateContrasenaUseCase
import com.padieer.asesoriapp.domain.validators.ValidateNombreUseCase
import com.padieer.asesoriapp.domain.validators.ValidateNumTelefono
import com.padieer.asesoriapp.domain.validators.ValidateNumeroControlUseCase
import com.padieer.asesoriapp.domain.validators.ValidateSemestreUseCase
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

data class FormDataErrors(
    val nombreError: String? = null,
    val apePatError: String? = null,
    val apeMatError: String? = null,
    val numControlError: String? = null,
    val numTelefonoError: String? = null,
    val semestreError: String? = null,
    val carreraError: String? = null,
    val contraError: String? = null,
    val contraRepiteError: String? = null
)

class CreaCuentaViewModel(
    private val carreraRepository: CarreraRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreaCuentaUIState())
    val uiState = _uiState.asStateFlow()

    private val _formDataState = MutableStateFlow(FormData())
    val formDataState = _formDataState.asStateFlow()

    private val _formErrorState = MutableStateFlow(FormDataErrors())
    val formErrorState = _formErrorState.asStateFlow()

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

    private suspend fun submit() {
        val nombreResult = ValidateNombreUseCase(formDataState.value.nombre).execute()
        val apePatResult = ValidateApellidoUseCase(formDataState.value.apePaterno).execute()
        val apeMatResult = ValidateApellidoUseCase(formDataState.value.apeMaterno).execute()
        val numControlResult = ValidateNumeroControlUseCase(formDataState.value.numControl).execute()
        val telefonoResult = ValidateNumTelefono(formDataState.value.numTelefono).execute()
        val semestreResult = ValidateSemestreUseCase(formDataState.value.numSemestre).execute()
        val carreraResult = ValidateCarreraUseCase(formDataState.value.carrera, carreraRepository).execute()
        val contrasenaResult = ValidateContrasenaUseCase(formDataState.value.contrasena).execute()
        val contraRepiteResult = ValidateContraRepiteUseCase(
            formDataState.value.contrasena,
            formDataState.value.contrasenaRepite)
            .execute()

        val isValid = listOf(
            nombreResult,
            apePatResult,
            apeMatResult,
            numControlResult,
            telefonoResult,
            semestreResult,
            carreraResult,
            contrasenaResult,
            contraRepiteResult

        ).all { it.isSuccessful }

        if (!isValid) {
            _formErrorState.update { it.copy(
                nombreError = nombreResult.errorMessage,
                apePatError = apePatResult.errorMessage,
                apeMatError = apeMatResult.errorMessage,
                numControlError = numControlResult.errorMessage,
                numTelefonoError = telefonoResult.errorMessage,
                semestreError = semestreResult.errorMessage,
                carreraError = carreraResult.errorMessage,
                contraError = contrasenaResult.errorMessage,
                contraRepiteError = contraRepiteResult.errorMessage
            ) }
            return
        }

        // Envia los datos
        Log.i("[SUCCESS]", "Datos enviados: ${formDataState.value}")
    }

    fun onEvent( event: CreaCuentaEvent ) {
        when (event) {
            is CreaCuentaEvent.NombreChanged -> {
                _formDataState.update { it.copy( nombre = event.nombre ) }
            }
            is CreaCuentaEvent.ApeMaternoChanged -> {
                _formDataState.update { it.copy( apeMaterno = event.apeMat ) }
            }
            is CreaCuentaEvent.ApePaternoChanged -> {
                _formDataState.update { it.copy( apePaterno = event.apePat ) }
            }
            is CreaCuentaEvent.ContrasenaChanged -> {
                _formDataState.update { it.copy( contrasena = event.contra ) }
            }
            is CreaCuentaEvent.ContrasenaRepiteChanged -> {
                _formDataState.update { it.copy( contrasenaRepite = event.contra ) }
            }
            is CreaCuentaEvent.NumControlChanged -> {
                _formDataState.update { it.copy( numControl = event.numControl ) }
            }
            is CreaCuentaEvent.NumTelefonoChanged -> {
                _formDataState.update { it.copy( numTelefono = event.telefono ) }
            }
            is CreaCuentaEvent.SemestreChanged -> {
                _formDataState.update { it.copy( numSemestre = event.semestre ) }
            }
            is CreaCuentaEvent.CarreraChanged -> {
                _formDataState.update { it.copy( carrera = event.carrera ) }
            }
            is CreaCuentaEvent.Submit -> {
                viewModelScope.launch { submit() }
            }
            is CreaCuentaEvent.InicioSesionClick -> {
                //
            }
        }
    }
}

fun CarreraModel.toUIModel(): Carrera {
    return Carrera(
        nombre = this.nombre
    )
}

data class Carrera(
    val nombre: String
)