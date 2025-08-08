package com.padieer.asesoriapp.di

import android.content.Context
import android.util.Log
import com.padieer.asesoriapp.crypto.LocalPreferencesSource
import com.padieer.asesoriapp.data.carrera.CarreraRepositoryImpl
import com.padieer.asesoriapp.data.carrera.sources.CacheCarreraSource
import com.padieer.asesoriapp.data.carrera.sources.RemoteCarreraSource
import com.padieer.asesoriapp.data.estudiante.EstudianteRepositoryImpl
import com.padieer.asesoriapp.data.estudiante.sources.RemoteEstudianteSource
import com.padieer.asesoriapp.data.horario.HorarioRepositoryImpl
import com.padieer.asesoriapp.data.horario.sources.LocalHorarioSource
import com.padieer.asesoriapp.data.horario.sources.RemoteHorarioSource
import com.padieer.asesoriapp.data.password.PasswordRepositoryImpl
import com.padieer.asesoriapp.data.password.sources.RemotePasswordSource
import com.padieer.asesoriapp.data.token.LoginRepositoryImpl
import com.padieer.asesoriapp.data.token.sources.RemoteTokenSource
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json

class AppModuleImpl(private val appContext: Context): AppModule {
    private val URL = "localhost"
    private val client = HttpClient(Android) {
        defaultRequest {
            host = URL
            contentType(ContentType.Application.Json)
            url {
                port = 8000
            }
        }
        install(ContentNegotiation) {
            json()
        }
        install(ResponseObserver) {
            onResponse { Log.d("KTOR-R-LOG: ", "$it") }
        }
        install(Logging) {
            logger = object: Logger {
                override fun log(message: String) { Log.v("KTOR-LOG: ", message) }
            }
        }
    }

    private val cacheCarreraSource by lazy {
        CacheCarreraSource()
    }

    private val remoteCarreraSource by lazy {
        RemoteCarreraSource(client = client)
    }

    private val remoteEstudianteSource by lazy {
        RemoteEstudianteSource(client = client)
    }

    private val remoteTokenSource by lazy {
        RemoteTokenSource(client = client)
    }

    private val remotePasswordSource by lazy {
        RemotePasswordSource(client = client)
    }

    private val localPreferencesSource by lazy {
        LocalPreferencesSource(context = appContext)
    }

    private val remoteHorarioSource by lazy {
        RemoteHorarioSource(client)
    }

    private val localHorarioSource by lazy {
        LocalHorarioSource()
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
            preferencesSource = localPreferencesSource
        )
    }

    override val loginRepository by lazy {
        LoginRepositoryImpl(
            localPreferencesSource = localPreferencesSource,
            remoteTokenSource = remoteTokenSource,
            estudianteRepository = estudianteRepository
        )
    }

    override val passwordRepository by lazy {
        PasswordRepositoryImpl(
            passwordSource = remotePasswordSource
        )
    }

    override val horarioRepository by lazy {
        HorarioRepositoryImpl(
            remoteHorarioSource = remoteHorarioSource,
            preferencesSource = localPreferencesSource,
            localHorarioSource = localHorarioSource,
        )
    }
}