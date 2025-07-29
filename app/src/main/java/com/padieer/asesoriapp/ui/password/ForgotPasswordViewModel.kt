package com.padieer.asesoriapp.ui.password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.padieer.asesoriapp.App
import com.padieer.asesoriapp.data.password.PasswordRepository
import com.padieer.asesoriapp.data.viewModelFactory
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.nav.Navigator
import com.padieer.asesoriapp.domain.validators.ValidateContraRepiteUseCase
import com.padieer.asesoriapp.domain.validators.ValidateContrasenaUseCase
import com.padieer.asesoriapp.domain.validators.ValidateNumTelefonoUseCase
import com.padieer.asesoriapp.domain.validators.ValidateNumeroControlUseCase
import com.padieer.asesoriapp.domain.validators.messageOrNull
import com.padieer.asesoriapp.ui.nav.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class FormData(
    val numControl: String = "",
    val numTelefono: String = "",
    val numControlError: String? = null,
    val numTelefonoError: String? = null,
    val validationState: ValidationState = ValidationState.UNVALIDATED,
)

data class OtpState(
    val code: List<Int?> = (1..6).map { null },
    val focusedIndex: Int? = null,
    val validState: ValidationState = ValidationState.UNVALIDATED
)

enum class ValidationState {
    UNVALIDATED,
    CHECKING_VALIDATION,
    NOT_VALID,
    VALID,
}

data class NewPasswordFormData(
    val password: String = "",
    val passwordConf: String = "",
    val passwordError: String? = null,
    val passwordConfError: String? = null,
    val validationState: ValidationState = ValidationState.UNVALIDATED,
)

sealed class UIState {
    data object Loading: UIState()
    data object UbicaEstudianteForm: UIState()
    data object OTPCodeForm: UIState()
    data object ResetPasswordForm: UIState()
}

class ForgotPasswordViewModel(
    val passwordRepository: PasswordRepository
): ViewModel() {

    private val _formDataState = MutableStateFlow(FormData())
    val formDataState = _formDataState.asStateFlow()

    private val _otpState = MutableStateFlow(OtpState())
    val otpState = _otpState.asStateFlow()

    private val _newPasswordFormState = MutableStateFlow(NewPasswordFormData())
    val newPasswordFormState = _newPasswordFormState.asStateFlow()

    private val _uiState = MutableStateFlow<UIState>(UIState.UbicaEstudianteForm)
    val uiState = _uiState.asStateFlow()

    val navigator = Navigator()

    private  var code: String = ""

    private fun submitForm() {

        val numControlResult = ValidateNumeroControlUseCase(formDataState.value.numControl).execute()
        val numTelefonoResult = ValidateNumTelefonoUseCase(formDataState.value.numTelefono).execute()

        val isValid = listOf(numControlResult, numTelefonoResult).all { it is Result.Success }

        if (!isValid) {
            _formDataState.update { it.copy(
                numControlError = numControlResult.messageOrNull(),
                numTelefonoError = numTelefonoResult.messageOrNull(),
                validationState = ValidationState.NOT_VALID
            ) }
            return
        }

        viewModelScope.launch {
            _formDataState.update { it.copy( validationState = ValidationState.CHECKING_VALIDATION ) }
            val result = passwordRepository.sendPasswordResetRequest(
                numControl = formDataState.value.numControl,
                numTelefono = formDataState.value.numTelefono
            )

            when (result) {
                is Result.Success -> {
                    _formDataState.update { it.copy(validationState = ValidationState.VALID) }
                    _uiState.update { UIState.OTPCodeForm }
                }
                is Result.Error -> {
                    _formDataState.update { it.copy(
                        validationState = ValidationState.NOT_VALID,
                    )}
                }
            }
        }
    }

    /**
     * Dado un codigo OTP de 6 caracteres, revisa
     */
    private fun validateOTPCode(code: String) {
        _otpState.update { it.copy(
            validState = ValidationState.CHECKING_VALIDATION
        ) }

        viewModelScope.launch {
            val result = passwordRepository.verifyPasswordCode(
                numControl = formDataState.value.numControl,
                numTelefono = formDataState.value.numTelefono,
                codigo = code
            )

            when (result) {
                is Result.Success -> {
                    _otpState.update { it.copy(
                        validState = ValidationState.VALID
                    ) }
                    this@ForgotPasswordViewModel.code = code
                    _uiState.update { UIState.ResetPasswordForm }
                }
                is Result.Error -> {
                    _otpState.update { it.copy(
                        validState = ValidationState.NOT_VALID
                    ) }
                }
            }
        }
    }

    private fun newPasswordSubmit() {
        _newPasswordFormState.update { it.copy( validationState = ValidationState.CHECKING_VALIDATION ) }

        val contrasena = newPasswordFormState.value.password
        val contraRepite = newPasswordFormState.value.passwordConf
        val passwordRes = ValidateContrasenaUseCase(contrasena).execute()
        val passConfRes = ValidateContraRepiteUseCase(contrasena, contraRepite).execute()

        val isValid = listOf(passwordRes, passConfRes).all { it is Result.Success }
        if ( !isValid ) {
            _newPasswordFormState.update { it.copy(
                passwordError = passwordRes.messageOrNull(),
                passwordConfError = passConfRes.messageOrNull(),
                validationState = ValidationState.UNVALIDATED,
            ) }
            return
        }

        viewModelScope.launch {
            val result = passwordRepository.sendNewPassword(
                password = contrasena,
                passwordConf = contraRepite,
                codigo = this@ForgotPasswordViewModel.code
            )

            when (result) {
                is Result.Success -> {
                    _newPasswordFormState.update { it.copy(
                        validationState = ValidationState.VALID
                    ) }

                    delay(3000L)
                    navigator.emit(Navigator.Action.GoTo(Screen.Auth))
                }
                is Result.Error -> {
                    _newPasswordFormState.update { it.copy(
                        validationState = ValidationState.NOT_VALID,
                    ) }
                }
            }
        }

    }

    /*
     * # OTP ViewModel
     *
     * Estas funciones son para el OTP
     *
     * @author Phillip Lackner
     * @see <a href="https://github.com/philipplackner/ComposeOTPInput">ComposeOTPInput</a>
     */

    // Cosas del OTP
    private fun otpEnterNumber(number: Int?, index: Int) {
        val newCode = otpState.value.code.mapIndexed { currentIndex, currentNumber ->
            if (currentIndex == index)
                number
            else currentNumber
        }
        val wasNumberRemoved = number == null

        _otpState.update {
            it.copy(
            code = newCode,
            focusedIndex = if ( wasNumberRemoved || it.code.getOrNull(index) != null ) {
                it.focusedIndex
            } else {
                getNextFocusedTextFieldIndex(
                    currentCode = it.code,
                    currentFocusedIndex = it.focusedIndex,
                )
            },
        ) }

        val codeIsComplete = newCode.none { it == null }
        if (codeIsComplete)
            validateOTPCode(newCode.joinToString(""))
    }

    private fun otpKeyboardBack() {
        val prevIndex = getPreviousFocusedIndex(otpState.value.focusedIndex)
        _otpState.update { it.copy(
            focusedIndex = prevIndex,
            code = it.code.mapIndexed { index, number ->
                if (index == prevIndex) null
                else number
            }
        ) }
    }

    private fun getPreviousFocusedIndex(currentIndex: Int?): Int? {
        return currentIndex?.minus(1)?.coerceAtLeast(0)
    }

    private fun getNextFocusedTextFieldIndex(currentCode: List<Int?>, currentFocusedIndex: Int?): Int? {
        if(currentFocusedIndex == null) {
            return null
        }

        if(currentFocusedIndex == 5) { // Maximo index
            return currentFocusedIndex
        }

        return getFirstEmptyFieldIndexAfterFocusedIndex(
            code = currentCode,
            currentFocusedIndex = currentFocusedIndex
        )
    }

    private fun getFirstEmptyFieldIndexAfterFocusedIndex(code: List<Int?>, currentFocusedIndex: Int): Int {
        code.forEachIndexed { index, number ->
            if(index <= currentFocusedIndex) {
                return@forEachIndexed
            }
            if(number == null) {
                return index
            }
        }
        return currentFocusedIndex
    }

    /**
     * Recibe todos los eventos de la pantalla ForgotPasswordScreen
     */
    fun onEvent(event: UIEvent) {
        when (event) {
            // Eventos de EncuentraEstudiante
            is UIEvent.NumeroControlChanged -> { _formDataState.update { it.copy(numControl = event.value) } }
            is UIEvent.NumeroTelefonoChanged -> { _formDataState.update { it.copy(numTelefono = event.value) } }
            is UIEvent.SubmitForm -> { submitForm() }

            // Eventos de OTP
            is UIEvent.OTPChangeFieldFocused -> { _otpState.update { it.copy( focusedIndex = event.index ) } }
            is UIEvent.OTPNumberEntered -> { otpEnterNumber(event.number, event.index) }
            UIEvent.OTPKeyboardBack -> { otpKeyboardBack() }

            // Eventos de NewPassword
            is UIEvent.NewPasswordChanged -> { _newPasswordFormState.update { it.copy( password = event.value ) } }
            is UIEvent.NewPasswordConfChanged -> { _newPasswordFormState.update { it.copy( passwordConf = event.value ) } }
            UIEvent.NewPasswordSubmit -> { newPasswordSubmit() }

            // Eventos de UI
            UIEvent.FormUbicaEstudiante -> { _uiState.update { UIState.UbicaEstudianteForm } }
            UIEvent.Loading -> { _uiState.update { UIState.Loading } }
            UIEvent.OTPCodeForm -> { _uiState.update { UIState.OTPCodeForm } }
        }
    }

    sealed class UIEvent {
        data class NumeroControlChanged(val value: String): UIEvent()
        data class NumeroTelefonoChanged(val value: String): UIEvent()
        data object SubmitForm: UIEvent()

        data class OTPChangeFieldFocused(val index: Int): UIEvent()
        data class OTPNumberEntered( val number: Int?, val index: Int ): UIEvent()
        data object OTPKeyboardBack: UIEvent()

        data class NewPasswordChanged(val value: String): UIEvent()
        data class NewPasswordConfChanged(val value: String): UIEvent()
        data object NewPasswordSubmit: UIEvent()

        data object Loading: UIEvent()
        data object FormUbicaEstudiante: UIEvent()
        data object OTPCodeForm: UIEvent()
    }

    companion object {
        fun Factory() = viewModelFactory {
            ForgotPasswordViewModel(
                passwordRepository = App.appModule.passwordRepository
            )
        }
    }
}