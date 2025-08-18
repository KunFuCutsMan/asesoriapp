package com.padieer.asesoriapp.ui.perfil

sealed class PerfilUIEvent {

    data object TelefonoClick: PerfilUIEvent()

    data object WhatsappClick: PerfilUIEvent()
}