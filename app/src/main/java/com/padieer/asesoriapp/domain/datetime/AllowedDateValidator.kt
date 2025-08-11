package com.padieer.asesoriapp.domain.datetime

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit
import java.util.Calendar
import java.util.TimeZone

/**
 * Interfaz que permite verificar que dicta que fechas son validas.
 */
@OptIn(ExperimentalMaterial3Api::class)
sealed interface AllowedDateValidator: SelectableDates {

    /**
     * Todas las fechas son válidas
     */
    object Default: AllowedDateValidator

    /**
     * La fecha debe caer en un dia de trabajo, y no en un fin de semana
     */
    object WeekDaysOnly: AllowedDateValidator {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.timeInMillis = utcTimeMillis
            return calendar[Calendar.DAY_OF_WEEK] != Calendar.SATURDAY && calendar[Calendar.DAY_OF_WEEK] != Calendar.SUNDAY
        }
    }

    /**
     * La fecha debe ser en el futuro, incluyendo hoy
     */
    object AfterOrEqualToday: AllowedDateValidator {

        private val now = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.timeInMillis = utcTimeMillis
            val isToday = calendar[Calendar.DAY_OF_YEAR] == now[Calendar.DAY_OF_YEAR]

            return isToday || now < calendar
        }

        override fun isSelectableYear(year: Int): Boolean {
            return now[Calendar.YEAR] <= year
        }
    }

    /**
     * Solo se permitirán las fechas hasta en 30 dias
     */
    object UntilNextMonth: AllowedDateValidator {

        private val now = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.timeInMillis = utcTimeMillis

            val instantNextMonth = now.toInstant().plus(31, ChronoUnit.DAYS)

            return calendar.toInstant().isBefore(instantNextMonth)
        }

        override fun isSelectableYear(year: Int): Boolean {
            val instantNextMonth = now.toInstant().plus(31, ChronoUnit.DAYS)
            val calendarYear = Calendar.getInstance()
            calendarYear.set(year, 1, 1)
            return instantNextMonth.isAfter(calendarYear.toInstant())
        }
    }

    /**
     * Para que una fecha sea permitida, debe cumplir todos los validadores ingresados
     */
    class All(vararg validators: AllowedDateValidator ): AllowedDateValidator {
        private val allValidators: List<AllowedDateValidator> = validators.toList()

        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return allValidators.all { it.isSelectableDate(utcTimeMillis) }
        }

        override fun isSelectableYear(year: Int): Boolean {
            return allValidators.all { it.isSelectableYear(year) }
        }
    }
}