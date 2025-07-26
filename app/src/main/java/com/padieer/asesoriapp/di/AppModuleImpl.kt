package com.padieer.asesoriapp.di

import android.content.Context
import com.padieer.asesoriapp.data.carrera.CarreraRepositoryImpl
import com.padieer.asesoriapp.data.carrera.sources.CacheCarreraSource
import com.padieer.asesoriapp.data.carrera.sources.RemoteCarreraSource
import com.padieer.asesoriapp.data.estudiante.EstudianteRepositoryImpl
import com.padieer.asesoriapp.data.estudiante.sources.RemoteEstudianteSource
import com.padieer.asesoriapp.data.password.PasswordRepositoryImpl
import com.padieer.asesoriapp.data.password.sources.RemotePasswordSource
import com.padieer.asesoriapp.data.token.LoginRepositoryImpl
import com.padieer.asesoriapp.data.token.sources.LocalTokenSource
import com.padieer.asesoriapp.data.token.sources.RemoteTokenSource
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

class AppModuleImpl(private val appContext: Context): AppModule {
    private val URL = "http://10.0.2.2/"
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json()
        }
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

    private val remoteEstudianteSource by lazy {
        RemoteEstudianteSource(
            client = client,
            initialURL = URL,
        )
    }

    private val remoteTokenSource by lazy {
        RemoteTokenSource(
            client = client,
            initialURL = URL
        )
    }

    private val remotePasswordSource by lazy {
        RemotePasswordSource(
            client = client,
            initialURL = URL
        )
    }

    private val localTokenSource by lazy {
        LocalTokenSource(context = appContext)
    }

    override val carreraRepository by lazy {
        CarreraRepositoryImpl(
            remoteCarreraSource = remoteCarreraSource,
            cacheCarreraSource = cacheCarreraSource,
        )
    }
    override val estudianteRepository by lazy {
        EstudianteRepositoryImpl(
            remoteEstudianteSource = remoteEstudianteSource,
            carreraRepository = carreraRepository,
        )
    }

    override val loginRepository by lazy {
        LoginRepositoryImpl(
            localTokenSource = localTokenSource,
            remoteTokenSource = remoteTokenSource,
        )
    }

    override val passwordRepository by lazy {
        PasswordRepositoryImpl(
            passwordSource = remotePasswordSource
        )
    }
}