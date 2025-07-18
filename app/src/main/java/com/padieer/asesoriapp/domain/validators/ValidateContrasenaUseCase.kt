package com.padieer.asesoriapp.domain.validators

import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.error.ValidationError

class ValidateContrasenaUseCase(private val contra: String) {
    fun execute(): ValidationResult {
        if (contra.isBlank())
            return Result.Error(ValidationError.ContrasenaError.NOT_EMPTY)

        if (contra.length < 8)
            return Result.Error(ValidationError.ContrasenaError.TOO_SHORT)

        if (contra.length > 32)
            return Result.Error(ValidationError.ContrasenaError.TOO_LONG)

        if (!contra.any { it.isLetter() })
            return Result.Error(ValidationError.ContrasenaError.NEEDS_LETTER)

        if (!contra.any { it.isDigit() })
            return Result.Error(ValidationError.ContrasenaError.NEEDS_DIGIT)

        if (!contra.hasMixedCase())
            return Result.Error(ValidationError.ContrasenaError.NOT_MIXED_CASE)

        return Result.Success(Unit)
    }
}