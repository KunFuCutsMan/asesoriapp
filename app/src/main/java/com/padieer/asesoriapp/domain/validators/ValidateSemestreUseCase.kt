package com.padieer.asesoriapp.domain.validators

class ValidateSemestreUseCase(private val semestre: Int) {

    fun execute(): ValidationResult {
        if (semestre > 0)
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Semestre no es válido"
            )

        return ValidationResult(isSuccessful = true)
    }
}