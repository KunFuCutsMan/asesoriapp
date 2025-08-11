package com.padieer.asesoriapp.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
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
import androidx.compose.ui.window.Dialog
import com.padieer.asesoriapp.domain.datetime.AllowedDateValidator
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme
import com.padieer.asesoriapp.ui.theme.isDarkTheme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun ModalDatePickerField(
    modifier: Modifier = Modifier,
    value: LocalDate?,
    label: String,
    selectableDates: AllowedDateValidator = AllowedDateValidator.Default,
    onValueChange: (LocalDate) -> Unit
) {
    val datePickerState = rememberDatePickerState(
        selectableDates = selectableDates
    )
    var dialogState by remember { mutableStateOf(false) }
    val dateFormatter = LocalDate.Format {
        day(); char('/'); monthNumber(); char('/'); year()
    }

    val disabledColor = if (isDarkTheme()) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surfaceDim

    OutlinedTextField(
        value = value?.run { dateFormatter.format(this) } ?: "",
        placeholder = {Text("--/--/----")},
        readOnly = true,
        label = { Text(label) },
        onValueChange = {},
        trailingIcon = { Icon(Icons.Outlined.DateRange, "") },
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(datePickerState) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    waitForUpOrCancellation(pass = PointerEventPass.Initial).let {
                        dialogState = true
                    }
                }
            }
    )

    if (dialogState) {
        DatePickerDialog(
            onDismissRequest = { dialogState = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val timestamp = datePickerState.selectedDateMillis
                        timestamp?.let {
                            val dateInstant = Instant.fromEpochMilliseconds(timestamp)
                            dateInstant.toLocalDateTime(TimeZone.UTC)
                            onValueChange(dateInstant.toLocalDateTime(TimeZone.UTC).date)
                        }

                        dialogState = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton( onClick = { dialogState = false } ) { Text("Cancelar") }
            }
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false,
                colors = DatePickerDefaults.colors().copy(
                    disabledYearContentColor = disabledColor,
                    disabledDayContentColor = disabledColor,
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalTimePickerField(
    modifier: Modifier = Modifier,
    label: String,
    value: LocalTime?,
    onValueChange: (LocalTime) -> Unit,
) {
    val timeFormatter = LocalTime.Format { hour(); char(':'); minute() }
    val clockDialColor = if (isDarkTheme()) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surfaceDim
    var dialogState by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState(
        initialHour = value?.hour ?: 0,
        initialMinute = value?.hour ?: 0,
    )

    OutlinedTextField(
        value = value?.run { timeFormatter.format(this) } ?: "",
        onValueChange = {},
        readOnly = true,
        label = { Text(label) },
        placeholder = { Text("--:--") },
        trailingIcon = {
            Icon(Icons.Outlined.DateRange, "")
        },
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(timePickerState) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    waitForUpOrCancellation(pass = PointerEventPass.Initial)?.let {
                        dialogState = true
                    }
                }
            },
    )

    if (dialogState) {
        Dialog(
            onDismissRequest = { dialogState = false },
        ) {
            Card {
                TimePicker(
                    state = timePickerState,
                    colors = TimePickerDefaults.colors().copy(
                        clockDialColor = clockDialColor,
                    ),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp),
                )

                Row(Modifier.padding(bottom = 16.dp)) {
                    Spacer(Modifier.weight(1f))
                    TextButton(
                        onClick = { dialogState = false }
                    ) { Text("Cancelar") }
                    TextButton(
                        onClick = {
                            onValueChange(LocalTime(
                                hour = timePickerState.hour,
                                minute = timePickerState.minute,
                                second = 0
                            ))
                            dialogState = false
                        }
                    ) { Text("OK") }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ModalDatePickerFieldPreview() {
    AsesoriAppTheme {
        Surface {
            ModalDatePickerField(
                value = null,
                label = "",
                onValueChange = {},
                selectableDates = AllowedDateValidator.All(
                    AllowedDateValidator.WeekDaysOnly,
                    AllowedDateValidator.AfterOrEqualToday,
                )
            )
        }
    }
}

@Preview
@Composable
private fun ModalTimePickerFieldPreview() {

    AsesoriAppTheme {
        Surface {
            ModalTimePickerField(
                label = "Hora",
                value = null,
                onValueChange = {}
            )
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
            ModalDatePickerField(
                label = "Fecha de Inicio",
                value = date,
                onValueChange = { date = it },
                selectableDates = AllowedDateValidator.All(
                    AllowedDateValidator.AfterOrEqualToday,
                    AllowedDateValidator.WeekDaysOnly,
                    AllowedDateValidator.UntilNextMonth,
                ),
            )

            Spacer(Modifier.height(30.dp))
            Text("Fecha: $date")

            Spacer(Modifier.height(100.dp))

            var time by remember { mutableStateOf<LocalTime?>(null) }
            ModalTimePickerField(
                label = "Hora",
                value = time,
                onValueChange = {time = it},
            )

            Spacer(Modifier.height(30.dp))
            Text("Hora: $time")
        }
    }
}