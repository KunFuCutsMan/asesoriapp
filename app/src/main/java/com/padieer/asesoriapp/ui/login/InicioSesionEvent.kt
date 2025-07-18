package com.padieer.asesoriapp.ui.login

sealed class InicioSesionEvent {

    data class NumControlChanged(val numControl: String): InicioSesionEvent()
    data class ContrasenaChanged(val contrasena: String): InicioSesionEvent()

    data object LoginClick: InicioSesionEvent()
    data object CreaCuentaScreenClick: InicioSesionEvent()
    data object ForgotPasswordClick: InicioSesionEvent()
}