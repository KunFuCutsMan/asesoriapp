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

class ForgotPasswordViewModel: ViewModel() {

    private val _formDataState = MutableStateFlow(FormData())
    val formDataState = _formDataState.asStateFlow()

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
        }
    }

    sealed class UIEvent {
        data class NumeroControlChanged(val value: String): UIEvent()
        data class NumeroTelefonoChanged(val value: String): UIEvent()
        data object SubmitForm: UIEvent()
    }

    companion object {
        fun Factory() = viewModelFactory {
            ForgotPasswordViewModel()
        }
    }
}