package com.padieer.asesoriapp.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.padieer.asesoriapp.domain.datetime.AllowedDateValidator
import com.padieer.asesoriapp.domain.datetime.HourLocalTimeProgression
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format.char
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalTime
import java.time.LocalDate as JavaLocalDate
import java.time.LocalTime as JavaLocalTime
import java.time.temporal.ChronoUnit

@Composable
fun ModalDatePickerField(
    modifier: Modifier = Modifier,
    value: LocalDate?,
    label: String,
    allowedDateValidator: AllowedDateValidator = AllowedDateValidator.Default,
    onValueChange: (LocalDate) -> Unit
) {
    val dialogState = rememberMaterialDialogState()
    val dateFormatter = LocalDate.Format {
        day(); char('/'); monthNumber(); char('/'); year()
    }

    OutlinedTextField(
        value = value?.run { dateFormatter.format(this) } ?: "",
        placeholder = {Text("--/--/----")},
        readOnly = true,
        label = { Text(label) },
        onValueChange = {},
        trailingIcon = { Icon(Icons.Outlined.DateRange, "") },
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(value) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    waitForUpOrCancellation(pass = PointerEventPass.Initial)?.let {
                        dialogState.show()
                    }
                }
            }
    )

    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton("OK")
            negativeButton("Cancelar")
        }
    ) {
        val now = JavaLocalDate.now()
        val nextYear = now.plus(1, ChronoUnit.YEARS)
        datepicker(
            title = label,
            initialDate = value?.toJavaLocalDate() ?: JavaLocalDate.now(),
            yearRange = IntRange(now.year, nextYear.year),
            allowedDateValidator = { allowedDateValidator.isAllowed(it.toKotlinLocalDate()) },
            onDateChange = { onValueChange(it.toKotlinLocalDate()) }
        )
    }

}

private fun JavaLocalDate.toKotlinLocalDate(): LocalDate {
    return LocalDate(
        year = this.year,
        month = this.monthValue,
        day = this.dayOfMonth,
    )
}

@Composable
fun ModalTimePickerField(
    modifier: Modifier = Modifier,
    label: String,
    timeRange: ClosedRange<JavaLocalTime> = JavaLocalTime.MIN..JavaLocalTime.MAX,
    value: LocalTime?,
    onValueChange: (LocalTime) -> Unit
) {
    val dialogState = rememberMaterialDialogState()
    val timeFormatter = LocalTime.Format { hour(); char(':'); minute() }

    OutlinedTextField(
        value = value?.run { timeFormatter.format(this) } ?: "",
        placeholder = {Text("--:--")},
        label = {Text(label)},
        readOnly = true,
        onValueChange = {},
        trailingIcon = { Icon(Icons.Outlined.DateRange, "") },
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(value) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    waitForUpOrCancellation(pass = PointerEventPass.Initial)?.let {
                        dialogState.show()
                    }
                }
            },
    )

    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton("OK")
            negativeButton("Cancelar")
        }
    ) {
        timepicker(
            title = label,
            initialTime = value?.toJavaLocalTime() ?: JavaLocalTime.now(),
            is24HourClock = true,
            waitForPositiveButton = true,
            timeRange = timeRange,
            onTimeChange = {
                onValueChange(LocalTime(
                    hour = it.hour,
                    minute = it.minute,
                    second = it.second,
                ))
            }
        )
    }
}

@Preview
@Composable
private fun ModalDatePickerFieldPreview() {
    AsesoriAppTheme {
        Surface {
            ModalDatePickerField(
                label = "Fecha",
                value = null,
                onValueChange = {},
            )
        }
    }
}

operator fun LocalTime.rangeTo(other: LocalTime) = HourLocalTimeProgression(this, other, 1)

@Preview
@Composable
private fun ModalTimePickerFieldPreview() {

    val timeStart = LocalTime(7, 0, 0)
    val timeEnd = LocalTime(20, 0, 0)

    AsesoriAppTheme {
        Surface {
            ModalTimePickerField(
                label = "Hora",
                value = null,
                onValueChange = {},
                timeRange = timeStart..timeEnd,
            )
        }
    }
}

@Preview
@Composable
private fun ModalDatePickerPreview() {
    val timeStart = LocalTime(7, 0, 0)
    val timeEnd = LocalTime(20, 0, 0)

    AsesoriAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var date by remember { mutableStateOf<LocalDate?>(null) }
            ModalDatePickerField(
                label = "Fecha de Inicio",
                value = date,
                onValueChange = { date = it },
                allowedDateValidator = AllowedDateValidator.All(
                    AllowedDateValidator.AfterOrEqualToday,
                    AllowedDateValidator.WeekDaysOnly,
                ),
            )

            Spacer(Modifier.height(30.dp))
            Text("Fecha: $date")

            Spacer(Modifier.height(100.dp))

            var time by remember { mutableStateOf<LocalTime?>(null) }
            ModalTimePickerField(
                label = "Hora",
                value = time,
                timeRange = timeStart..timeEnd,
                onValueChange = { time = it },
            )

            Spacer(Modifier.height(30.dp))
            Text("Hora: $time")
        }
    }
}