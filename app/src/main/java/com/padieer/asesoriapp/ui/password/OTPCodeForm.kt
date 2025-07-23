package com.padieer.asesoriapp.ui.password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.padieer.asesoriapp.App
import com.padieer.asesoriapp.di.FakeAppModule
import com.padieer.asesoriapp.ui.common.ErrorText
import com.padieer.asesoriapp.ui.common.OtpInputField
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme

@Composable
internal fun OTPCodeForm(viewModel: ForgotPasswordViewModel, modifier: Modifier = Modifier) {

    val state by viewModel.otpState.collectAsStateWithLifecycle()
    val focusRequesters = remember { List(6){ FocusRequester() } }
    val focusManager = LocalFocusManager.current
    val keyboardManager = LocalSoftwareKeyboardController.current

    LaunchedEffect(state.focusedIndex) {
        state.focusedIndex?.let { index -> focusRequesters.getOrNull(index)?.requestFocus() }
    }

    LaunchedEffect(state.code, keyboardManager) {
        val allNumbersEntered = state.code.all { it != null }
        if (allNumbersEntered) {
            focusRequesters.forEach{ it.freeFocus() }
            focusManager.clearFocus()
            keyboardManager?.hide()
        }
    }

    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            state.code.forEachIndexed { index, number ->
                OtpInputField(
                    number = number,
                    focusRequester = focusRequesters[index],
                    onFocusChanged = { viewModel.onEvent(ForgotPasswordViewModel.UIEvent.OTPChangeFieldFocused(index)) },
                    onNumberChanged = { viewModel.onEvent(ForgotPasswordViewModel.UIEvent.OTPNumberEntered(it, index)) },
                    onKeyboardBack = {viewModel.onEvent(ForgotPasswordViewModel.UIEvent.OTPKeyboardBack)},
                    modifier = Modifier.weight(1f).aspectRatio(1f)
                )
            }
        }
        when (state.validState) {
            ValidationState.CHECKING_VALIDATION -> {
                LinearProgressIndicator(Modifier.padding(vertical = 16.dp, horizontal = 32.dp))
            } // Se espera la validacion externa
            ValidationState.NOT_VALID -> {
                ErrorText("El código no es válido.")
            } // No es valido
            else -> {}
        }
    }
}

@Preview
@Composable
private fun OTPCodeFormPreview() {
    App.appModule = FakeAppModule()
    val viewModel: ForgotPasswordViewModel = viewModel( factory = ForgotPasswordViewModel.Factory() )
    AsesoriAppTheme {
        OTPCodeForm(
            viewModel = viewModel,
            modifier = Modifier.size(100.dp)
        )
    }
}