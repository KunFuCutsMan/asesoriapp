package com.padieer.asesoriapp.domain.validators

class ValidateNumeroControlUseCase(private val numControl: String) {

    fun execute(): ValidationResult {
        if (numControl.isBlank())
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Numero de Control no debe de ser vacío"
            )

        if (!numControl.isNumeric())
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Numero de Control debe de ser numérico"
            )

        if (numControl.length != 8)
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Numero de Control debe de ser de 8 números"
            )

        return ValidationResult( isSuccessful = true )
    }
}