package com.padieer.asesoriapp.ui.cuentas

sealed class CreaCuentaEvent {

    data class NombreChanged(val nombre: String): CreaCuentaEvent()
    data class ApePaternoChanged(val apePat: String): CreaCuentaEvent()
    data class ApeMaternoChanged(val apeMat: String): CreaCuentaEvent()
    data class NumControlChanged(val numControl: String): CreaCuentaEvent()
    data class NumTelefonoChanged(val telefono: String): CreaCuentaEvent()
    data class SemestreChanged(val semestre: Int): CreaCuentaEvent()
    data class CarreraChanged(val carrera: String): CreaCuentaEvent()
    data class ContrasenaChanged(val contra: String): CreaCuentaEvent()
    data class ContrasenaRepiteChanged(val contra: String): CreaCuentaEvent()

    data object Submit: CreaCuentaEvent()

    data object InicioSesionClick: CreaCuentaEvent()
}