package com.padieer.asesoriapp.ui.asesoria.peticion

import androidx.lifecycle.ViewModel
import com.padieer.asesoriapp.App
import com.padieer.asesoriapp.data.asesoria.AsesoriaRepository
import com.padieer.asesoriapp.data.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PedirAsesoriaViewModel(
    private val asesoriaRepository: AsesoriaRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<PedirAsesoriaUIState>(PedirAsesoriaUIState.Loading)
    val uiState = _uiState.asStateFlow()

    companion object {
        fun factory() = viewModelFactory {
            PedirAsesoriaViewModel(
                asesoriaRepository = App.appModule.asesoriaRepository
            )
        }
    }
}