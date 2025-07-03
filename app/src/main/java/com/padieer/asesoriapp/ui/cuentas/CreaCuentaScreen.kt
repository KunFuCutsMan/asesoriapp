package com.padieer.asesoriapp.ui.cuentas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.padieer.asesoriapp.App
import com.padieer.asesoriapp.R
import com.padieer.asesoriapp.data.viewModelFactory
import com.padieer.asesoriapp.ui.common.FullScreenLoading
import com.padieer.asesoriapp.ui.common.OutlinedDropdown
import com.padieer.asesoriapp.ui.common.OutlinedTextFieldConMaximo
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme

@Composable
fun CreaCuentaScreen() {
    val viewModel = viewModel<CreaCuentaViewModel>(
        factory = viewModelFactory {
            CreaCuentaViewModel(
                carreraRepository = App.appModule.carreraRepository
            )
        }
    )
    CreaCuentaScreen(viewModel)
}

@Composable
fun CreaCuentaScreen(
    viewModel: CreaCuentaViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column (
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
            text = "Llena el formulario para crear tu cuenta",
            style = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Center)
        )

        OutlinedCard {
           if (uiState.isLoading) {
               FullScreenLoading()
           } else {
               FormCreaCuenta(viewModel, uiState.carrerasList)
           }
        }

        Button(
            onClick = { viewModel.onEvent(CreaCuentaEvent.Submit) },
            contentPadding = ButtonDefaults.TextButtonContentPadding
        ) { Text("Crear Cuenta", fontSize = 16.sp) }

        TextButton(onClick = { viewModel.onEvent(CreaCuentaEvent.InicioSesionClick) }) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }
    }
}

@Composable
fun FormCreaCuenta(
    viewModel: CreaCuentaViewModel,
    carrerasList: List<Carrera>,
) {
    val formDataState by viewModel.formDataState.collectAsStateWithLifecycle()

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextFieldConMaximo(
            value = formDataState.nombre,
            maxLength = 32,
            label = { Text("Nombre") },
            onValueChange = { viewModel.onEvent( CreaCuentaEvent.NombreChanged(it) ) }
        )
        OutlinedTextFieldConMaximo(
            value = formDataState.apePaterno,
            maxLength = 32,
            label = { Text("Apellido Paterno") },
            onValueChange = { viewModel.onEvent( CreaCuentaEvent.ApePaternoChanged(it) ) }
        )
        OutlinedTextFieldConMaximo(
            value = formDataState.apeMaterno,
            maxLength = 32,
            label = { Text("Apellido Materno") },
            onValueChange = { viewModel.onEvent( CreaCuentaEvent.ApeMaternoChanged(it) ) }
        )
        OutlinedTextFieldConMaximo(
            value = formDataState.numControl,
            maxLength = 8,
            label = { Text("Número de Control") },
            keyboardType = KeyboardType.Number,
            onValueChange = { viewModel.onEvent( CreaCuentaEvent.NumControlChanged(it) ) }
        )
        OutlinedTextFieldConMaximo(
            value = formDataState.numTelefono,
            maxLength = 10,
            label = { Text("Número Telefónico") },
            keyboardType = KeyboardType.Phone,
            onValueChange = { viewModel.onEvent( CreaCuentaEvent.NumTelefonoChanged(it) ) }
        )
        OutlinedDropdown(
            label = { Text("Semestre") },
            onValueChange = { viewModel.onEvent( CreaCuentaEvent.SemestreChanged(it.toInt()) ) },
            // TODO Que se obtenga del repo o algo
            data = (1..15).toList().map { it.toString() }
        )
        OutlinedDropdown(
            label = { Text("Carrera") },
            onValueChange = { viewModel.onEvent( CreaCuentaEvent.CarreraChanged(it) ) },
            // TODO que se obtenga de un repo
            data = carrerasList.map { it.nombre }
        )
        OutlinedTextFieldConMaximo(
            value = formDataState.contrasena,
            maxLength = 32,
            label = { Text("Contraseña") },
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = { viewModel.onEvent( CreaCuentaEvent.ContrasenaChanged(it) ) }
        )
        OutlinedTextFieldConMaximo(
            value = formDataState.contrasenaRepite,
            maxLength = 32,
            label = { Text("Confirma tu contraseña") },
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = { viewModel.onEvent( CreaCuentaEvent.ContrasenaRepiteChanged(it) ) }
        )
    }
}

@Preview
@Composable
fun CreaCuentaScreenPreview() {
    AsesoriAppTheme {
        CreaCuentaScreen()
    }
}