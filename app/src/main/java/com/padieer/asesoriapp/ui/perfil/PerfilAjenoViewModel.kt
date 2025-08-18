package com.padieer.asesoriapp.ui.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.padieer.asesoriapp.App
import com.padieer.asesoriapp.data.estudiante.EstudianteRepository
import com.padieer.asesoriapp.data.viewModelFactory
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.model.toUIModel
import com.padieer.asesoriapp.domain.phone.CallPhoneUseCase
import com.padieer.asesoriapp.domain.phone.ContactWhatsappUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PerfilAjenoViewModel(
    private val estudianteID: Int,
    private val estudianteRepository: EstudianteRepository,
    private val callPhoneUseCase: CallPhoneUseCase,
    private val contactWhatsappUseCase: ContactWhatsappUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow<PerfilUiState>(PerfilUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var telefono: String? = null

    init {
        viewModelScope.launch { loadInitialData() }
    }

    suspend fun loadInitialData() {
        val estudianteRes = estudianteRepository.getEstudianteByID(estudianteID)
        when (estudianteRes) {
            is Result.Error -> {
                _uiState.update { PerfilUiState.Error(estudianteRes.error.toString()) }
            }
            is Result.Success -> {
                val estudiante = estudianteRes.data
                telefono = estudiante.numeroTelefono
                _uiState.update { PerfilUiState.EstudiantePerfil(
                    estudiante = estudiante.toUIModel(),
                    carrera = estudiante.carrera.toUIModel(),
                    especialidad = estudiante.especialidad?.toUIModel(),
                ) }
            }
        }
    }

    fun llamaPerfil() {
        telefono?.let { callPhoneUseCase(it) }
    }

    fun contactaPorWhats() {
        telefono?.let { contactWhatsappUseCase(it) }
    }

    fun onEvent(event: PerfilUIEvent) {
        when (event) {
            PerfilUIEvent.TelefonoClick -> viewModelScope.launch {
                llamaPerfil()
            }
            PerfilUIEvent.WhatsappClick -> viewModelScope.launch {
                contactaPorWhats()
            }
        }
    }

    companion object {
        fun Factory(estudianteID: Int) = viewModelFactory {
            PerfilAjenoViewModel(
                estudianteRepository = App.appModule.estudianteRepository,
                estudianteID = estudianteID,
                callPhoneUseCase = App.appModule.callPhoneUseCase,
                contactWhatsappUseCase = App.appModule.contactWhatsappUseCase
            )
        }
    }
}