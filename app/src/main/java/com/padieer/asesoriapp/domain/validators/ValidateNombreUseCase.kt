package com.padieer.asesoriapp.domain.validators

import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.error.ValidationError

class ValidateNombreUseCase(private val nombre: String) {

    fun execute(): ValidationResult {
        if (nombre.isBlank())
            return Result.Error(ValidationError.NombreError.NOT_EMPTY)

        if (nombre.length > 32)
            return Result.Error(ValidationError.NombreError.TOO_LONG)

        if (!nombre.isAlpha())
            return Result.Error(ValidationError.NombreError.NOT_ALPHA)

        return Result.Success(Unit)
    }
}