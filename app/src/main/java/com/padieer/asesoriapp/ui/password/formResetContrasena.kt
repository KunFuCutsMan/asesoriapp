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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.padieer.asesoriapp.ui.common.ErrorText
import com.padieer.asesoriapp.ui.common.OutlinedTextFieldConMaximo

@Composable
fun FormResetContrasena(viewModel: ForgotPasswordViewModel, modifier: Modifier = Modifier) {
    val state by viewModel.newPasswordFormState.collectAsStateWithLifecycle()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.verticalScroll( rememberScrollState() )
    ) {
        OutlinedTextFieldConMaximo(
            value = state.password,
            maxLength = 32,
            label = { Text("Contraseña") },
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = { viewModel.onEvent( ForgotPasswordViewModel.UIEvent.NewPasswordChanged(it) ) }
        )

        if ( state.passwordError != null ) ErrorText(state.passwordError!!)

        Spacer(Modifier.height(20.dp))

        OutlinedTextFieldConMaximo(
            value = state.passwordConf,
            maxLength = 32,
            label = { Text("Confirma tu contraseña") },
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = { viewModel.onEvent( ForgotPasswordViewModel.UIEvent.NewPasswordConfChanged(it) ) }
        )

        if ( state.passwordConfError != null ) ErrorText(state.passwordConfError!!)

        when (state.validationState) {
            ValidationState.UNVALIDATED -> {
                Spacer(Modifier.height(20.dp))
            }
            ValidationState.CHECKING_VALIDATION -> {
                LinearProgressIndicator(Modifier.padding(vertical = 16.dp, horizontal = 32.dp))
            }
            ValidationState.NOT_VALID -> {
                ErrorText("Hubo un error al validar el código")
            }
            ValidationState.VALID -> {
                Text("Se reinició tu contraseña exitósamente. Redirigiendo a pantalla de inicio de sesión...")
            }
        }

        Button(
            onClick = { viewModel.onEvent( ForgotPasswordViewModel.UIEvent.NewPasswordSubmit ) },
            contentPadding = ButtonDefaults.TextButtonContentPadding,
        ) { Text("Reiniciar contraseña") }
    }
}