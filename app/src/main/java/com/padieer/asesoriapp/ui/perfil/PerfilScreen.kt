package com.padieer.asesoriapp.ui.perfil

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.padieer.asesoriapp.ui.common.ErrorText
import com.padieer.asesoriapp.ui.common.FullScreenLoading
import com.padieer.asesoriapp.ui.common.Perfil

@Composable
fun PerfilScreen() {
    val viewModel: PerfilViewModel = viewModel( factory = PerfilViewModel.Factory() )
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    when (uiState) {
        is PerfilUiState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                ErrorText(
                    text = (uiState as PerfilUiState.Error).error,
                    textAlign = TextAlign.Center
                )
            }
        }
        is PerfilUiState.EstudiantePerfil -> {
            val (estudiante, carrera) = (uiState as PerfilUiState.EstudiantePerfil)
            Perfil(
                estudiante = estudiante,
                carrera = carrera,
                onTelefonoClick = {},
                onEditarClick = {},
                isEditable = true)
        }
        PerfilUiState.Loading -> {
            FullScreenLoading()
        }
    }
}

