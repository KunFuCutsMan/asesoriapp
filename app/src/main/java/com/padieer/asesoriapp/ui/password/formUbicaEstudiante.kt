package com.padieer.asesoriapp.ui.password

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.padieer.asesoriapp.ui.common.ErrorText
import com.padieer.asesoriapp.ui.common.OutlinedTextFieldConMaximo

@Composable
internal fun FormUbicaEstudiante(viewModel: ForgotPasswordViewModel, modifier: Modifier = Modifier) {
    val formDataState by viewModel.formDataState.collectAsStateWithLifecycle()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.verticalScroll( rememberScrollState() )
    ) {
        OutlinedTextFieldConMaximo(
            label = { Text("Número de Control") },
            keyboardType = KeyboardType.Number,
            maxLength = 8,
            value = formDataState.numControl,
            onValueChange = { viewModel.onEvent(ForgotPasswordViewModel.UIEvent.NumeroControlChanged(it)) }
        )

        if (formDataState.numControlError != null) ErrorText(formDataState.numControlError!!)

        Spacer(Modifier.height(20.dp))

        OutlinedTextFieldConMaximo(
            label = { Text("Número Telefónico") },
            keyboardType = KeyboardType.Phone,
            maxLength = 10,
            value = formDataState.numTelefono,
            onValueChange = { viewModel.onEvent(ForgotPasswordViewModel.UIEvent.NumeroTelefonoChanged(it)) }
        )

        if (formDataState.numTelefonoError != null) ErrorText(formDataState.numTelefonoError!!)

        when (formDataState.validationState) {
            ValidationState.UNVALIDATED -> {
                Spacer(Modifier.height(20.dp))
            }
            ValidationState.CHECKING_VALIDATION -> {
                LinearProgressIndicator(Modifier.padding(vertical = 16.dp, horizontal = 32.dp))
            }
            ValidationState.NOT_VALID -> {
                ErrorText("Hubo un error al validar el código")
            }
            else -> {}
        }

        Button(
            onClick = { viewModel.onEvent(ForgotPasswordViewModel.UIEvent.SubmitForm) },
            contentPadding = ButtonDefaults.TextButtonContentPadding
        ) { Text("Enviar datos", style = MaterialTheme.typography.labelLarge) }
    }
}