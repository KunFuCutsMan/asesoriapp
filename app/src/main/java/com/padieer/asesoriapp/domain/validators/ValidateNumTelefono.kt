package com.padieer.asesoriapp.domain.validators

import android.util.Patterns

class ValidateNumTelefono(private val numero: String) {

    fun execute(): ValidationResult {
        if (numero.isBlank())
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Número Telefónico no debe de ser vacío"
            )

        if (!numero.isNumeric())
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Número Telefónico debe ser numérico"
            )

        if (numero.length != 10)
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Número Telefónico debe ser de 10 dígitos"
            )

        if ( !Patterns.PHONE.matcher(numero).matches() ) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Número Telefónico no es válido"
            )
        }

        return ValidationResult(isSuccessful = true)
    }
}