package com.padieer.asesoriapp.di

import android.content.Context
import com.padieer.asesoriapp.data.carrera.CarreraRepository
import com.padieer.asesoriapp.data.carrera.CarreraRepositoryImpl
import com.padieer.asesoriapp.data.carrera.sources.CacheCarreraSource
import com.padieer.asesoriapp.data.carrera.sources.FakeCarreraSource
import com.padieer.asesoriapp.data.carrera.sources.RemoteCarreraSource
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface AppModule {
    val carreraRepository: CarreraRepository
}

class AppModuleImpl(private val appContext: Context): AppModule {

    private val URL = "http://10.0.2.2/"
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json()
        }
    }

    private val fakeCarreraSource by lazy {
        FakeCarreraSource()
    }

    private val cacheCarreraSource by lazy {
        CacheCarreraSource()
    }

    private val remoteCarreraSource by lazy {
        RemoteCarreraSource(
            client = client,
            initialURL = URL
        )
    }

    override val carreraRepository by lazy {
        CarreraRepositoryImpl(
            remoteCarreraSource = remoteCarreraSource,
            cacheCarreraSource = cacheCarreraSource,
        )
    }
}