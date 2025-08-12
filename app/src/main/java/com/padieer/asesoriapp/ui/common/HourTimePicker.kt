package com.padieer.asesoriapp.ui.common

import com.padieer.asesoriapp.R
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme
import com.sd.lib.compose.wheel_picker.FVerticalWheelPicker
import com.sd.lib.compose.wheel_picker.rememberFWheelPickerState

fun Int.toHoraFormat(): String {
    val hora = if (this < 10) "0$this" else "$this"
    return "$hora:00"
}

@Composable
fun ModalHourTimePicker(
    modifier: Modifier = Modifier,
    label: String,
    hourState: Int?,
    from: Int,
    to: Int,
    onValueChange: (Int) -> Unit,
) {
    var dialogState by remember { mutableStateOf(false) }
    val wheelState = rememberFWheelPickerState()

    OutlinedTextField(
        value = hourState?.run { toHoraFormat() } ?: "",
        readOnly = true,
        onValueChange = {},
        label = { Text(label) },
        placeholder = { Text("--:--") },
        trailingIcon = { Icon(painterResource(R.drawable.calendar_clock), contentDescription = "") },
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(true) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    waitForUpOrCancellation(pass = PointerEventPass.Initial)?.let {
                        dialogState = true
                    }
                }
            }
    )

    if (dialogState) {
        Dialog(
            onDismissRequest = { dialogState = false },
        ) {
            Card {
                FVerticalWheelPicker(
                    modifier = Modifier
                        .padding( horizontal = 32.dp, vertical = 16.dp),
                    state = wheelState,
                    count = to - from + 1
                ) { index ->
                    Text(
                        text =(index+from).toHoraFormat(),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }

                Row(Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp)) {
                    Spacer(Modifier.weight(1f))
                    TextButton(
                        onClick = { dialogState = false }
                    ) { Text("Cancelar") }
                    TextButton(
                        onClick = {
                            onValueChange(wheelState.currentIndex + from)
                            dialogState = false
                        }
                    ) { Text("OK") }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ModalHourTimePickerPreview() {
    AsesoriAppTheme {
        Surface {
            ModalHourTimePicker(
                hourState = 9,
                label = "Hora",
                from = 7,
                to = 20,
                onValueChange = {}
            )
        }
    }
}

@Preview
@Composable
private fun ModalHourTimePreview() {
    AsesoriAppTheme {
        Surface(Modifier.fillMaxSize()) {
            Box(Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
                var hourState by remember { mutableIntStateOf(9) }
                ModalHourTimePicker(
                    hourState = hourState,
                    label = "Hora",
                    from = 7,
                    to = 20,
                    onValueChange = { hourState = it }
                )
            }
        }
    }
}