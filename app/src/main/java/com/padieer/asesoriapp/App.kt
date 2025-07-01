package com.padieer.asesoriapp

import android.app.Application
import com.padieer.asesoriapp.di.AppModule
import com.padieer.asesoriapp.di.AppModuleImpl

class App: Application() {
    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}