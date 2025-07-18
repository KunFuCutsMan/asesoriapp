package com.padieer.asesoriapp.domain.validators

import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.error.ValidationError

class ValidateNumeroControlUseCase(private val numControl: String) {

    fun execute(): ValidationResult {
        if (numControl.isBlank())
            return Result.Error(ValidationError.NumeroControlError.NOT_EMPTY)

        if (!numControl.isNumeric())
            return Result.Error(ValidationError.NumeroControlError.NOT_NUMERIC)

        if (numControl.length != 8)
            return Result.Error(ValidationError.NumeroControlError.WRONG_LENGTH)

        return Result.Success(Unit)
    }
}