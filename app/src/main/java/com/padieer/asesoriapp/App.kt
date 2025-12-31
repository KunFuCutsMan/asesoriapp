package com.padieer.asesoriapp

import android.app.Application

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        Dependencies.initialize(this)
    }
}