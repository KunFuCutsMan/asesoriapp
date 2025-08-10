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
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import java.time.LocalDate as JavaLocalDate
import java.time.LocalTime as JavaLocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
fun ModalDatePickerField(
    modifier: Modifier = Modifier,
    label: String,
    allowedDateValidator: (JavaLocalDate) -> Boolean = {true},
    onValueChange: (LocalDate) -> Unit
) {
    val dialogState = rememberMaterialDialogState()
    var chosenDate by remember { mutableStateOf<JavaLocalDate?>(null) }

    OutlinedTextField(
        value = chosenDate?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) ?: "",
        placeholder = {Text("--/--/----")},
        readOnly = true,
        label = { Text(label) },
        onValueChange = {},
        trailingIcon = { Icon(Icons.Outlined.DateRange, "") },
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(chosenDate) {
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
            yearRange = IntRange(
                start = now.year,
                endInclusive = nextYear.year),
            allowedDateValidator = allowedDateValidator,
            onDateChange = {
                chosenDate = it
                onValueChange(LocalDate(year = it.year, month = it.monthValue, day = it.dayOfMonth))
            }
        )
    }

}

@Composable
fun ModalTimePickerField(
    modifier: Modifier = Modifier,
    label: String,
    timeRange: ClosedRange<JavaLocalTime> = JavaLocalTime.MIN..JavaLocalTime.MAX,
    onValueChange: (LocalTime) -> Unit
) {
    val dialogState = rememberMaterialDialogState()
    var chosenTime by remember { mutableStateOf<JavaLocalTime?>(null) }

    OutlinedTextField(
        value = chosenTime?.format(DateTimeFormatter.ofPattern("kk:mm")) ?: "",
        placeholder = {Text("--:--")},
        label = {Text(label)},
        readOnly = true,
        onValueChange = {},
        trailingIcon = { Icon(Icons.Outlined.DateRange, "") },
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(chosenTime) {
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
            is24HourClock = true,
            waitForPositiveButton = true,
            timeRange = timeRange,
            onTimeChange = {
                chosenTime = it
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
            ModalDatePickerField(label = "Fecha") {}
        }
    }
}

@Preview
@Composable
private fun ModalTimePickerFieldPreview() {
    AsesoriAppTheme {
        Surface {
            ModalTimePickerField(label = "Hora") {}
        }
    }
}

@Preview
@Composable
private fun ModalDatePickerPreview() {
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
            ModalDatePickerField(label = "Fecha de Inicio") { date = it }
            Spacer(Modifier.height(30.dp))
            Text("Fecha: $date")

            Spacer(Modifier.height(100.dp))
            var time by remember { mutableStateOf<LocalTime?>(null) }
            ModalTimePickerField(label = "Hora") { time = it }
            Spacer(Modifier.height(30.dp))
            Text("Hora: $time")
        }
    }
}