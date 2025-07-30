package com.padieer.asesoriapp.ui.password

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.padieer.asesoriapp.App
import com.padieer.asesoriapp.di.FakeAppModule
import com.padieer.asesoriapp.ui.common.FullScreenLoading
import com.padieer.asesoriapp.ui.common.LogoPadieerSinLetras
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme

@Composable
fun ForgotPasswordScreen(navController: NavController? = null) {
    val viewModel: ForgotPasswordViewModel = viewModel(factory = ForgotPasswordViewModel.Factory())

    val context = LocalContext.current
    LaunchedEffect(true) {
        viewModel.navigator.channel.collect {
            viewModel.navigator.consumeAction(it, navController, context)
        }
    }

    Scaffold { padding ->
        ForgotPasswordScreen(
            viewModel = viewModel,
            modifier = Modifier
                .padding(padding)
                .consumeWindowInsets(padding)
        )
    }
}

val instrucciones = """
    Rellena el siguiente formulario con tus datos,
    una vez que los envíes, recibirás un código en
    un mensaje SMS que tendrás que ingresar en el campo de texto.
""".trimIndent()

@Composable
fun ForgotPasswordScreen(viewModel: ForgotPasswordViewModel, modifier: Modifier = Modifier) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(32.dp)
    ) {
        LogoPadieerSinLetras(Modifier.size(180.dp))

        Text(
            text = "¿Ólvidaste tu contraseña?",
            style = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Center)
        )
        Text(
            text = instrucciones,
            style = MaterialTheme.typography.bodyMedium
        )

        val m = Modifier.fillMaxWidth().padding(16.dp)
        OutlinedCard(
            modifier = Modifier
                .fillMaxHeight(1f)
        ) {
            when (uiState) {
                UIState.Loading -> {  FullScreenLoading() }
                UIState.OTPCodeForm -> { OTPCodeForm(viewModel, modifier = m) }
                UIState.UbicaEstudianteForm -> { FormUbicaEstudiante(viewModel, modifier = m) }
                UIState.ResetPasswordForm -> { FormResetContrasena(viewModel, modifier = m) }
            }
        }
    }
}

@Preview
@Composable
fun ForgotPasswordScreenPreview() {
    App.appModule = FakeAppModule()
    val viewModel: ForgotPasswordViewModel = viewModel(factory = ForgotPasswordViewModel.Factory())
    // viewModel.onEvent( ForgotPasswordViewModel.UIEvent.Loading )
    AsesoriAppTheme {
        ForgotPasswordScreen(viewModel = viewModel)
    }
}