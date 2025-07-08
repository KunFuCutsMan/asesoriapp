package com.padieer.asesoriapp.domain.validators

import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.error.ValidationError

class ValidateContraRepiteUseCase(private val contra: String, private val repite: String) {

    fun execute(): Result<Unit, ValidationError.ContrasenaRepiteError> {
        if ( contra.isBlank() || repite.isBlank() )
            return Result.Error(ValidationError.ContrasenaRepiteError.NOT_EMPTY)

        if (contra != repite)
            return Result.Error(ValidationError.ContrasenaRepiteError.NOT_EQUAL)

        return Result.Success(Unit)
    }
}