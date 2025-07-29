package com.padieer.asesoriapp.ui.app

sealed class AppEvent {

    data object LogOutClick: AppEvent()
}