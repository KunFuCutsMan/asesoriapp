package com.padieer.asesoriapp.di

import android.content.Context
import com.padieer.asesoriapp.data.carrera.CarreraRepository
import com.padieer.asesoriapp.data.carrera.CarreraRepositoryImpl
import com.padieer.asesoriapp.data.carrera.sources.FakeCarreraSource

interface AppModule {
    val carreraRepository: CarreraRepository
}

class AppModuleImpl(private val appContext: Context): AppModule {

    //val client = HttpClient(CIO)

    private val fakeCarreraSource by lazy {
        FakeCarreraSource()
    }

    override val carreraRepository by lazy {
        CarreraRepositoryImpl(
            remoteCarreraSource = fakeCarreraSource
        )
    }
}