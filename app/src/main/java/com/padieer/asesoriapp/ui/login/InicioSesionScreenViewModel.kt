package com.padieer.asesoriapp.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class InicioSesionScreenViewModel : ViewModel() {

    var numeroControl by mutableStateOf("")
        private set

    var contrasena by mutableStateOf("")

    fun changeNumeroControl(it: String): Unit {
        numeroControl = it
    }

    fun changeContrasena(it: String): Unit {
        contrasena = it
    }
}