package com.padieer.asesoriapp.ui.password

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.padieer.asesoriapp.App
import com.padieer.asesoriapp.di.FakeAppModule
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme

@Composable
internal fun OTPCodeForm(viewModel: ForgotPasswordViewModel, modifier: Modifier = Modifier) {

    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row {

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