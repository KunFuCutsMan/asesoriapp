package com.padieer.asesoriapp.domain.validators

class ValidateApellidoUseCase(private val apellido: String) {

    fun execute(): ValidationResult {
        if (apellido.isBlank())
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Apellido no debe de ser vacío"
            )

        if (apellido.length > 32)
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Apellido no puede ser mayor a 32 carácteres"
            )

        if ( !apellido.isAlpha() )
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Apellido solo puede ser contener letras"
            )

        return ValidationResult( isSuccessful = true )
    }
}