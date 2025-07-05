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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.padieer.asesoriapp.R
import com.padieer.asesoriapp.ui.common.ErrorText
import com.padieer.asesoriapp.ui.common.OutlinedTextFieldConMaximo
import com.padieer.asesoriapp.ui.nav.Screen
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme

@Composable
fun InicioSesionScreen(navController: NavController? = null) {
    val viewModel = viewModel<InicioSesionScreenViewModel>()

    LaunchedEffect(true) {
        viewModel.eventChannel.collect {
            when (it) {
                is InicioSesionScreenViewModel.Event.CreaCuentaNav -> {
                    navController?.navigate(Screen.CreaCuentaScreen.route)
                }
            }
        }
    }

    InicioSesionScren(
        viewModel = viewModel,
    )
}

@Composable
fun InicioSesionScren(
    viewModel: InicioSesionScreenViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(32.dp))

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
            modifier = Modifier.padding( horizontal = 16.dp )
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo PADIEER",
                modifier = Modifier.size(240.dp)
            )

            Text("Inicio de Sesión", fontSize = 16.sp)

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding( horizontal = 32.dp )
            ) {
                OutlinedTextFieldConMaximo(
                    value = uiState.numeroControl,
                    maxLength = 8,
                    label = { Text("Número de Control") },
                    onValueChange = { viewModel.onEvent( InicioSesionEvent.NumControlChanged(it) ) }
                )
                if (uiState.numControlError != null) ErrorText(uiState.numControlError!!)

                Spacer(Modifier.height(20.dp))

                OutlinedTextFieldConMaximo(
                    value = uiState.contrasena,
                    maxLength = 32,
                    label = { Text("Contraseña") },
                    keyboardType = KeyboardType.Password,
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = { viewModel.onEvent( InicioSesionEvent.ContrasenaChanged(it) ) }
                )
                if (uiState.contraError != null) ErrorText(uiState.contraError!!)

                Spacer(Modifier.height(20.dp))
            }



            Button(
                onClick = { viewModel.onEvent(InicioSesionEvent.LoginClick) },
                contentPadding = ButtonDefaults.TextButtonContentPadding
            ) {
                Text("▶ Iniciar Sesión", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                TextButton(onClick = { viewModel.onEvent(InicioSesionEvent.CreaCuentaScreenClick) }) {
                    Text("¿Eres Nuevo? ¡Crea una cuenta!")
                }

                TextButton(onClick = {}) {
                    Text("¿Perdiste la contraseña? Presiona aquí")
                }
            }

            Spacer(Modifier.height(32.dp))
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