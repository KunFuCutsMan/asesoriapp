package com.padieer.asesoriapp.domain.datetime

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.daysUntil
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

/**
 * Interfaz que permite verificar que dicta que fechas son validas.
 */
sealed interface AllowedDateValidator {

    fun isAllowed(date: LocalDate): Boolean {
        return true
    }

    /**
     * Todas las fechas son válidas
     */
    object Default: AllowedDateValidator

    /**
     * La fecha debe caer en un dia de trabajo, y no en un fin de semana
     */
    object WeekDaysOnly: AllowedDateValidator {
        override fun isAllowed(date: LocalDate): Boolean {
            return date.dayOfWeek != DayOfWeek.SATURDAY && date.dayOfWeek != DayOfWeek.SUNDAY
        }
    }

    /**
     * La fecha debe ser en el futuro, incluyendo hoy
     */
    object AfterOrEqualToday: AllowedDateValidator {
        @OptIn(ExperimentalTime::class)
        override fun isAllowed(date: LocalDate): Boolean {
            val timeZone = TimeZone.currentSystemDefault()
            val now = Clock.System.now()
            val instant = date.atStartOfDayIn(timeZone)
            return now.daysUntil(instant, timeZone) >= 0
        }
    }

    /**
     * Para que una fecha sea permitida, debe cumplir todos los validadores ingresados
     */
    class All(vararg validators: AllowedDateValidator ): AllowedDateValidator {
        private val allValidators: List<AllowedDateValidator> = validators.toList()

        override fun isAllowed(date: LocalDate): Boolean {
            return allValidators.all { it.isAllowed(date) }
        }
    }
}