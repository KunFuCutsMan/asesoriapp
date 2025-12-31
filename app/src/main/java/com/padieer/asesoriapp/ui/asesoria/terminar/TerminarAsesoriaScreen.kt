package com.padieer.asesoriapp.ui.asesoria.terminar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.padieer.asesoriapp.ui.common.textFields.NormalTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TerminarAsesoriaScreen(
    viewModel: TerminarAsesoriaViewModel,
    onBackPressed: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Terminar Asesoria") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            NormalTextField(
                value = uiState.codigoAsesor,
                onValueChange = { viewModel.onEvent(TerminarAsesoriaEvent.OnCodigoAsesorChanged(it)) },
                labelText = "Código del Asesor",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            NormalTextField(
                value = uiState.codigoEstudiante,
                onValueChange = { viewModel.onEvent(TerminarAsesoriaEvent.OnCodigoEstudianteChanged(it)) },
                labelText = "Código del Estudiante",
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(onClick = { viewModel.onEvent(TerminarAsesoriaEvent.OnSubmit) }) {
                Text("Terminar Asesoría")
            }
        }
    }
}