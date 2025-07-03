package com.padieer.asesoriapp.domain.validators

class ValidateNombreUseCase(private val nombre: String) {

    fun execute(): ValidationResult {
        if (nombre.isBlank())
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Nombre no debe de ser vacío"
            )

        if (nombre.length > 32)
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Nombre no puede ser mayor a 32 carácteres"
            )

        if ( !nombre.isAlpha() )
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Nombre solo puede ser contener letras"
            )

        return ValidationResult( isSuccessful = true )
    }
}