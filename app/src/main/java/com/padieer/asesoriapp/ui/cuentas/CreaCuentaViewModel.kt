package com.padieer.asesoriapp.ui.cuentas

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.padieer.asesoriapp.data.carrera.CarreraModel
import com.padieer.asesoriapp.data.carrera.CarreraRepository
import com.padieer.asesoriapp.data.estudiante.EstudianteRepository
import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.error.message
import com.padieer.asesoriapp.domain.validators.ValidateApellidoUseCase
import com.padieer.asesoriapp.domain.validators.ValidateCarreraUseCase
import com.padieer.asesoriapp.domain.validators.ValidateContraRepiteUseCase
import com.padieer.asesoriapp.domain.validators.ValidateContrasenaUseCase
import com.padieer.asesoriapp.domain.validators.ValidateNombreUseCase
import com.padieer.asesoriapp.domain.validators.ValidateNumTelefono
import com.padieer.asesoriapp.domain.validators.ValidateNumeroControlUseCase
import com.padieer.asesoriapp.domain.validators.ValidateSemestreUseCase
import io.ktor.http.isSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
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
    private val estudianteRepository: EstudianteRepository,
    private val carreraRepository: CarreraRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreaCuentaUIState())
    val uiState = _uiState.asStateFlow()

    private val _formDataState = MutableStateFlow(FormData())
    val formDataState = _formDataState.asStateFlow()

    private val _formErrorState = MutableStateFlow(FormDataErrors())
    val formErrorState = _formErrorState.asStateFlow()

    private val _eventChannel = Channel<Event>()
    val navigationEvents = _eventChannel.receiveAsFlow()

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

        ).all { it is Result.Success }

        if (!isValid) {
            _formErrorState.update { it.copy(
                nombreError = (nombreResult as Result.Error).error.message(),
                apePatError = (apePatResult as Result.Error).error.message(),
                apeMatError = (apeMatResult as Result.Error).error.message(),
                numControlError = (numControlResult as Result.Error).error.message(),
                numTelefonoError = (telefonoResult as Result.Error).error.message(),
                semestreError = (semestreResult as Result.Error).error.message(),
                carreraError = (carreraResult as Result.Error).error.message(),
                contraError = (contrasenaResult as Result.Error).error.message(),
                contraRepiteError = (contraRepiteResult as Result.Error).error.message()
            ) }
            return
        }

        Log.i("[SUCCESS]", "Datos enviados: ${formDataState.value}")

        val result = estudianteRepository.insertEstudiante(
            nombre = formDataState.value.nombre,
            apellidoPaterno = formDataState.value.apePaterno,
            apellidoMaterno = formDataState.value.apeMaterno,
            numeroControl = formDataState.value.numControl,
            numeroTelefono = formDataState.value.numTelefono,
            semestre = formDataState.value.numSemestre,
            carrera = formDataState.value.carrera,
            contrasena = formDataState.value.contrasena,
        )

        when (result) {
            is Result.Success -> {
                viewModelScope.launch { _eventChannel.send(Event.Success) }
            }
            is Result.Error -> {
                when (result.error) {
                    DataError.Network.BAD_PARAMS -> viewModelScope.launch {
                        _eventChannel.send(Event.Toast("Hubo un error al validar los datos"))
                    }
                    DataError.Network.UNKWOWN -> viewModelScope.launch {
                        _eventChannel.send((Event.Toast("Hubo un error y no sabemos que es :(")))
                    }
                }
            }
        }
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
                viewModelScope.launch { _eventChannel.send(Event.InicioSesionNav) }
            }
        }
    }

    sealed class Event {
        data object InicioSesionNav: Event()
        data class Toast(val message: String): Event()
        data object Success: Event()
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