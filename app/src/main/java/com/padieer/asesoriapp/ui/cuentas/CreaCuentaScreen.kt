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
import com.padieer.asesoriapp.R
import com.padieer.asesoriapp.ui.common.OutlinedDropdown
import com.padieer.asesoriapp.ui.common.OutlinedTextFieldConMaximo
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme

@Composable
fun CreaCuentaScreen() {
    val viewModel = viewModel<CreaCuentaViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CreaCuentaScreen(
        uiState = uiState,
        onNombreChange = { viewModel.setNombre(it) },
        onApePaternoChange = { viewModel.setApePaterno(it) },
        onApeMaternoChange = { viewModel.setApeMaterno(it) },
        onNumeroControlChange = { viewModel.setNumeroControl(it) },
        onNumeroTelefonoChange = { viewModel.setNumeroTelefono(it) },
        onSemestreChange = { viewModel.setSemestre(it.toInt()) },
        onCarreraChange = { viewModel.setCarrera(it) },
        onContrasenaChange = { viewModel.setContrasena(it) },
        onContrasenaRepiteChange = { viewModel.setContrasenaRepite(it) },
        onCreaCuentaClick = {},
        onIniciaSesionClick = {},
    )
}

@Composable
fun CreaCuentaScreen(
    uiState: CreaCuentaUIState,
    onNombreChange: (String) -> Unit,
    onApePaternoChange: (String) -> Unit,
    onApeMaternoChange: (String) -> Unit,
    onNumeroControlChange: (String) -> Unit,
    onNumeroTelefonoChange: (String) -> Unit,
    onSemestreChange: (String) -> Unit,
    onCarreraChange: (String) -> Unit,
    onContrasenaChange: (String) -> Unit,
    onContrasenaRepiteChange: (String) -> Unit,
    onCreaCuentaClick: () -> Unit,
    onIniciaSesionClick: () -> Unit,
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .background( MaterialTheme.colorScheme.background )
            .padding(32.dp)
            .verticalScroll( rememberScrollState() )
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
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                OutlinedTextFieldConMaximo(
                    value = uiState.nombre,
                    maxLength = 32,
                    label = { Text("Nombre") },
                    onValueChange = onNombreChange
                )
                OutlinedTextFieldConMaximo(
                    value = uiState.apePaterno,
                    maxLength = 32,
                    label = { Text("Apellido Paterno") },
                    onValueChange = onApePaternoChange
                )
                OutlinedTextFieldConMaximo(
                    value = uiState.apeMaterno,
                    maxLength = 32,
                    label = { Text("Apellido Materno") },
                    onValueChange = onApeMaternoChange
                )
                OutlinedTextFieldConMaximo(
                    value = uiState.numControl,
                    maxLength = 8,
                    label = { Text("Número de Control") },
                    keyboardType = KeyboardType.Number,
                    onValueChange = onNumeroControlChange
                )
                OutlinedTextFieldConMaximo(
                    value = uiState.numTelefono,
                    maxLength = 10,
                    label = { Text("Número Telefónico") },
                    keyboardType = KeyboardType.Phone,
                    onValueChange = onNumeroTelefonoChange
                )
                OutlinedDropdown(
                    onValueChange = onSemestreChange,
                    label = { Text("Semestre") },
                    // TODO Que se obtenga del repo o algo
                    data = (1..15).toList().map { it.toString() }
                )
                OutlinedDropdown(
                    onValueChange = onCarreraChange,
                    label = { Text("Carrera") },
                    // TODO que se obtenga de un repo
                    data = arrayListOf("Mecatronica", "Industrial", "Renovables")
                )
                OutlinedTextFieldConMaximo(
                    value = uiState.contrasena,
                    maxLength = 32,
                    label = { Text("Contraseña") },
                    keyboardType = KeyboardType.Password,
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = onContrasenaChange
                )
                OutlinedTextFieldConMaximo(
                    value = uiState.contrasenaRepite,
                    maxLength = 32,
                    label = { Text("Confirma tu contraseña") },
                    keyboardType = KeyboardType.Password,
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = onContrasenaRepiteChange
                )
            }
        }

        Button(
            onClick = { onCreaCuentaClick() },
            contentPadding = ButtonDefaults.TextButtonContentPadding
        ) { Text("Crear Cuenta", fontSize = 16.sp) }

        TextButton(onClick = { onIniciaSesionClick() }) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }
    }
}

@Preview
@Composable
fun CreaCuentaScreenPreview() {
    AsesoriAppTheme {
        CreaCuentaScreen()
    }
}