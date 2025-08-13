package com.padieer.asesoriapp.domain.validators

import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.error.ValidationError
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class ValidateHoraFinalUseCase(
    private val horaInicial: LocalTime,
    private val horaFinal: LocalTime,
) {

    @OptIn(ExperimentalTime::class)
    fun execute(): ValidationResult {

        if (horaFinal.minute != 0 || horaFinal.second != 0 || horaFinal.nanosecond != 0)
            return Result.Error(ValidationError.HoraFinalError.HOURS_ONLY)

        if (horaFinal < HORA_MINIMA)
            return Result.Error(ValidationError.HoraFinalError.TOO_EARLY)

        if (horaFinal > HORA_MAXIMA)
            return Result.Error(ValidationError.HoraFinalError.TOO_LATE)

        if (horaFinal <= horaInicial)
            return Result.Error(ValidationError.HoraFinalError.BEFORE_INITIAL)

        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        if (horaFinal < now.time)
            return Result.Error(ValidationError.HoraInicioError.TOO_EARLY)

        return Result.Success(Unit)
    }

    companion object {
        val HORA_MINIMA = LocalTime(8, 0, 0)
        val HORA_MAXIMA = LocalTime(21,0,0)
    }
}