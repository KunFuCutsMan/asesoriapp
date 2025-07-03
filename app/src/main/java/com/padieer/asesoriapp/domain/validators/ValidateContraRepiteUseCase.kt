package com.padieer.asesoriapp.domain.validators

class ValidateContraRepiteUseCase(private val contra: String, private val repite: String) {

    fun execute(): ValidationResult {
        if (contra != repite)
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "La contraseña no es la misma"
            )

        return ValidationResult(isSuccessful = true)
    }
}