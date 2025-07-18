package com.padieer.asesoriapp.domain.validators

import android.util.Patterns
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.error.ValidationError

class ValidateNumTelefono(private val numero: String) {

    fun execute(): ValidationResult {
        if (numero.isBlank())
            return Result.Error(ValidationError.TelefonoError.NOT_EMPTY)

        if (!numero.isNumeric())
            return Result.Error(ValidationError.TelefonoError.NOT_NUMERIC)

        if (numero.length != 10)
            return Result.Error(ValidationError.TelefonoError.WRONG_LENGTH)

        if ( !Patterns.PHONE.matcher(numero).matches() ) {
            return Result.Error(ValidationError.TelefonoError.NOT_VALID)
        }

        return Result.Success(Unit)
    }
}