package com.padieer.asesoriapp.domain.validators

import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.error.ValidationError

class ValidateApellidoUseCase(private val apellido: String) {

    fun execute(): Result<Unit, ValidationError.ApellidoError> {
        if (apellido.isBlank())
            return Result.Error(ValidationError.ApellidoError.NOT_EMPTY)

        if (apellido.length > 32)
            return Result.Error(ValidationError.ApellidoError.TOO_LONG)

        if (!apellido.isAlpha())
            return Result.Error(ValidationError.ApellidoError.NOT_ALPHA)

        return Result.Success(Unit)
    }
}