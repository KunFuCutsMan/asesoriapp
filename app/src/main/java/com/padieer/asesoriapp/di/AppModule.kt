package com.padieer.asesoriapp.di

import android.content.Context
import com.padieer.asesoriapp.data.carrera.CarreraRepository
import com.padieer.asesoriapp.data.carrera.CarreraRepositoryImpl
import com.padieer.asesoriapp.data.carrera.sources.FakeCarreraSource
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

interface AppModule {
    val carreraRepository: CarreraRepository
}

class AppModuleImpl(private val appContext: Context): AppModule {

    private val URL = "https://10.0.2.2:8000/api/v1/"
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    private val fakeCarreraSource by lazy {
        FakeCarreraSource()
    }

    override val carreraRepository by lazy {
        CarreraRepositoryImpl(
            remoteCarreraSource = fakeCarreraSource
        )
    }
}