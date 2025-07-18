package com.padieer.asesoriapp.domain.validators

import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.error.ValidationError

class ValidateSemestreUseCase(private val semestre: Int) {

    fun execute(): ValidationResult {
        if (semestre < 0)
            return Result.Error(ValidationError.SemestreError.NOT_VALID)

        return Result.Success(Unit)
    }
}