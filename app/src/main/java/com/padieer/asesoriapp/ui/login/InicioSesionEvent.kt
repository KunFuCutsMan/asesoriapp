package com.padieer.asesoriapp.ui.login

sealed class InicioSesionEvent {

    data class NumControlChanged(val numControl: String): InicioSesionEvent()
    data class ContrasenaChanged(val contrasena: String): InicioSesionEvent()

    object LoginClick: InicioSesionEvent()
    object CreaCuentaScreenClick: InicioSesionEvent()
}