package com.padieer.asesoriapp.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.padieer.asesoriapp.R
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme

@Composable
fun InicioSesionScreen() {
    val viewModel = viewModel<InicioSesionScreenViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    InicioSesionScren(
        uiState = uiState,
        onNumeroControlChanged = { viewModel.setNumeroControl(it) },
        onContrasenaChanged = { viewModel.setContrasena(it) },
        onInicioSesionClick = { viewModel.login() },
        onCreaCuentaClick = {},
        onContrasenaPerdidaClick = {}
    )
}

@Composable
fun InicioSesionScren(
        uiState: InicioSesionUIState,
        onNumeroControlChanged: (String) -> Unit,
        onContrasenaChanged: (String) -> Unit,
        onInicioSesionClick: () -> Unit,
        onCreaCuentaClick: () -> Unit,
        onContrasenaPerdidaClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(vertical = 32.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row {
            Spacer(modifier = Modifier.weight(1f))
            Button(
                shape = RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp),
                contentPadding = ButtonDefaults.TextButtonContentPadding,
                modifier = Modifier.height(40.dp),
                onClick = {}
            ) {
                Text("✪ ¿Eres Asesor? Presiona Aquí", fontSize = 14.sp)
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo PADIEER",
                modifier = Modifier.size(240.dp)
            )

            Text("Inicio de Sesión", fontSize = 16.sp)

            OutlinedTextField(
                value = uiState.numeroControl,
                label = { Text("Número de Control") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                    showKeyboardOnFocus = true
                ),
                onValueChange = { onNumeroControlChanged(it) }
            )

            OutlinedTextField(
                value = uiState.contrasena,
                label = { Text("Contraseña") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { onContrasenaChanged(it) }
            )

            Button(
                onClick = { onInicioSesionClick() },
                contentPadding = ButtonDefaults.TextButtonContentPadding
            ) {
                Text("▶ Iniciar Sesión", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                TextButton(onClick = { onCreaCuentaClick() }) {
                    Text("¿Eres Nuevo? ¡Crea una cuenta!")
                }

                TextButton(onClick = { onContrasenaPerdidaClick() }) {
                    Text("¿Perdiste la contraseña? Presiona aquí")
                }
            }


        }
    }
}

@Preview
@Composable
fun InicioSesionScreenPreview() {
    AsesoriAppTheme {
        InicioSesionScreen()
    }
}