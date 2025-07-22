package com.padieer.asesoriapp.ui.password

import androidx.lifecycle.ViewModel
import com.padieer.asesoriapp.data.viewModelFactory
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.validators.ValidateNumTelefonoUseCase
import com.padieer.asesoriapp.domain.validators.ValidateNumeroControlUseCase
import com.padieer.asesoriapp.domain.validators.messageOrNull
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class FormData(
    val numControl: String = "",
    val numTelefono: String = "",
    val numControlError: String? = null,
    val numTelefonoError: String? = null,
)

sealed class UIState {

    data object Loading: UIState()
    data object UbicaEstudianteForm: UIState()
    data object OTPCodeForm: UIState()
}

class ForgotPasswordViewModel: ViewModel() {

    private val _formDataState = MutableStateFlow(FormData())
    val formDataState = _formDataState.asStateFlow()

    private val _uiState = MutableStateFlow<UIState>(UIState.UbicaEstudianteForm)
    val uiState = _uiState.asStateFlow()

    private fun submitForm() {
        val numControlResult = ValidateNumeroControlUseCase(formDataState.value.numControl).execute()
        val numTelefonoResult = ValidateNumTelefonoUseCase(formDataState.value.numTelefono).execute()

        val isValid = listOf(numControlResult, numTelefonoResult).all { it is Result.Success }

        if (!isValid) {
            _formDataState.update { it.copy(
                numControlError = numControlResult.messageOrNull(),
                numTelefonoError = numTelefonoResult.messageOrNull(),
            ) }
            return
        }
    }

    fun onEvent(event: UIEvent) {
        when (event) {
            is UIEvent.NumeroControlChanged -> { _formDataState.update { it.copy(numControl = event.value) } }
            is UIEvent.NumeroTelefonoChanged -> { _formDataState.update { it.copy(numTelefono = event.value) } }
            is UIEvent.SubmitForm -> { submitForm() }
            UIEvent.FormUbicaEstudiante -> { _uiState.update { UIState.UbicaEstudianteForm } }
            UIEvent.Loading -> { _uiState.update { UIState.Loading } }
            UIEvent.OTPCodeForm -> { _uiState.update { UIState.OTPCodeForm } }
        }
    }

    sealed class UIEvent {
        data class NumeroControlChanged(val value: String): UIEvent()
        data class NumeroTelefonoChanged(val value: String): UIEvent()
        data object SubmitForm: UIEvent()

        data object Loading: UIEvent()
        data object FormUbicaEstudiante: UIEvent()
        data object OTPCodeForm: UIEvent()
    }

    companion object {
        fun Factory() = viewModelFactory {
            ForgotPasswordViewModel()
        }
    }
}