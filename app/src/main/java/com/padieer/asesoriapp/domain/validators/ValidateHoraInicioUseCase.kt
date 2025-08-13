package com.padieer.asesoriapp.domain.validators

import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.error.ValidationError
import kotlinx.datetime.LocalTime
import kotlin.time.ExperimentalTime

class ValidateHoraInicioUseCase(
    private val hora: LocalTime
) {

    @OptIn(ExperimentalTime::class)
    fun execute(): ValidationResult {

        if (hora.minute != 0 || hora.second != 0 || hora.nanosecond != 0)
            return Result.Error(ValidationError.HoraInicioError.HOURS_ONLY)

        if (hora < HORA_MINIMA)
            return Result.Error(ValidationError.HoraInicioError.TOO_EARLY)

        if (hora > HORA_MAXIMA)
            return Result.Error(ValidationError.HoraInicioError.TOO_LATE)

        return Result.Success(Unit)
    }

    companion object {
        val HORA_MINIMA = LocalTime(7, 0, 0)
        val HORA_MAXIMA = LocalTime(20, 0, 0)
    }
}