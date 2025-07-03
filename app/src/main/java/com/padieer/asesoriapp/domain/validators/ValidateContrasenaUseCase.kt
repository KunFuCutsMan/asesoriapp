package com.padieer.asesoriapp.domain.validators

class ValidateContrasenaUseCase(private val contra: String) {
    fun execute(): ValidationResult {
        if (contra.isBlank())
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Contraseña no debe ser vacía"
            )

        if (contra.length < 8)
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Contraseña debe tener al menos 8 caracteres"
            )

        if (contra.length > 32)
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Contraseña no debe exceder 32 caracteres"
            )

        if (!contra.any { it.isLetter() })
            return ValidationResult(
                isSuccessful = false,
                "Contraseña debe incluir un caracter"
            )

        if (!contra.any { it.isDigit() })
            return ValidationResult(
                isSuccessful = false,
                "Contraseña debe incluir un dígito"
            )

        if (!contra.hasMixedCase())
            return ValidationResult(
                isSuccessful = false,
                "Contraseña debe incluir al menos un caracter mayúsculo y otro minúsculo"
            )

        return ValidationResult(isSuccessful = true)
    }
}