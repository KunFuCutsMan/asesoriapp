package com.padieer.asesoriapp.ui.password

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.padieer.asesoriapp.App
import com.padieer.asesoriapp.R
import com.padieer.asesoriapp.di.FakeAppModule
import com.padieer.asesoriapp.ui.common.ErrorText
import com.padieer.asesoriapp.ui.common.OutlinedTextFieldConMaximo
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme

@Composable
fun ForgotPasswordScreen(navController: NavController? = null) {
    val viewModel: ForgotPasswordViewModel = viewModel(factory = ForgotPasswordViewModel.Factory())

    ForgotPasswordScreen(viewModel = viewModel)
}

val instrucciones = """
    Rellena el siguiente formulario con tus datos,
    una vez que los envíes, recibirás un código en
    un mensaje SMS que tendrás que ingresar en el
    campo de texto.
""".trimIndent()

@Composable
fun ForgotPasswordScreen(viewModel: ForgotPasswordViewModel) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(32.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(id = R.drawable.logosinletras),
            contentDescription = "Logo PADIEER",
            modifier = Modifier.size(180.dp)
        )
        Text(
            text = "¿Ólvidaste tu contraseña?",
            style = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Center)
        )
        Text(
            text = instrucciones,
            style = MaterialTheme.typography.bodyMedium
        )

        OutlinedCard { FormCuenta(viewModel) }

        Button(
            onClick = { viewModel.onEvent(ForgotPasswordViewModel.UIEvent.SubmitForm) },
            contentPadding = ButtonDefaults.TextButtonContentPadding
        ) { Text("Enviar datos", fontSize = 16.sp) }
    }
}

@Composable
private fun FormCuenta(viewModel: ForgotPasswordViewModel) {
    val formDataState by viewModel.formDataState.collectAsStateWithLifecycle()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
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

        Spacer(Modifier.height(20.dp))
    }
}

@Preview
@Composable
fun ForgotPasswordScreenPreview() {
    App.appModule = FakeAppModule()
    val viewModel: ForgotPasswordViewModel = viewModel(factory = ForgotPasswordViewModel.Factory())
    AsesoriAppTheme {
        ForgotPasswordScreen(viewModel = viewModel)
    }
}