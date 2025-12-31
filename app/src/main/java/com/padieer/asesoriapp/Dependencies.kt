
package com.padieer.asesoriapp

import android.content.Context
import com.padieer.asesoriapp.crypto.LocalPreferencesSource
import com.padieer.asesoriapp.data.asesoria.AsesoriaRepository
import com.padieer.asesoriapp.data.asesoria.AsesoriaRepositoryImpl
import com.padieer.asesoriapp.data.asesoria.sources.RemoteAsesoriaSource
import com.padieer.asesoriapp.data.asignatura.AsignaturaRepository
import com.padieer.asesoriapp.data.asignatura.AsignaturaRepositoryImpl
import com.padieer.asesoriapp.data.asignatura.sources.LocalAsignaturaSource
import com.padieer.asesoriapp.data.asignatura.sources.RemoteAsignaturaSource
import com.padieer.asesoriapp.data.carrera.CarreraRepository
import com.padieer.asesoriapp.data.carrera.CarreraRepositoryImpl
import com.padieer.asesoriapp.data.carrera.sources.CacheCarreraSource
import com.padieer.asesoriapp.data.carrera.sources.RemoteCarreraSource
import com.padieer.asesoriapp.data.estudiante.EstudianteRepository
import com.padieer.asesoriapp.data.estudiante.EstudianteRepositoryImpl
import com.padieer.asesoriapp.data.estudiante.sources.LocalEstudianteSource
import com.padieer.asesoriapp.data.estudiante.sources.RemoteEstudianteSource
import com.padieer.asesoriapp.data.horario.HorarioRepository
import com.padieer.asesoriapp.data.horario.HorarioRepositoryImpl
import com.padieer.asesoriapp.data.horario.sources.LocalHorarioSource
import com.padieer.asesoriapp.data.horario.sources.RemoteHorarioSource
import com.padieer.asesoriapp.data.password.PasswordRepository
import com.padieer.asesoriapp.data.password.PasswordRepositoryImpl
import com.padieer.asesoriapp.data.password.sources.RemotePasswordSource
import com.padieer.asesoriapp.data.token.LoginRepository
import com.padieer.asesoriapp.data.token.LoginRepositoryImpl
import com.padieer.asesoriapp.data.token.sources.RemoteTokenSource
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object Dependencies {

    private lateinit var applicationContext: Context

    fun initialize(context: Context) {
        applicationContext = context.applicationContext
    }

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = true
    }

    private val httpClient: HttpClient by lazy {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(json)
            }
            install(Logging) {
                logger = Logger.ANDROID
                level = io.ktor.client.plugins.logging.LogLevel.ALL
            }
            defaultRequest {
                // !! ESTA ES LA LÍNEA CLAVE !!
                // Apunta al localhost de la PC desde el emulador de Android
                // Reemplaza "nombre_de_tu_api" con el nombre de la carpeta de tu proyecto en htdocs
                url("http://10.0.2.2/nombre_de_tu_api/public/")
            }
        }
    }

    // ... (el resto del archivo no cambia)
    private val remoteTokenSource: RemoteTokenSource by lazy {
        RemoteTokenSource(httpClient)
    }

    private val localPreferencesSource: LocalPreferencesSource by lazy {
        LocalPreferencesSource(applicationContext)
    }

    private val remoteEstudianteSource: RemoteEstudianteSource by lazy {
        RemoteEstudianteSource(httpClient)
    }

    private val localEstudianteSource: LocalEstudianteSource by lazy {
        LocalEstudianteSource()
    }

    private val remoteAsesoriaSource: RemoteAsesoriaSource by lazy {
        RemoteAsesoriaSource(httpClient)
    }

    private val remoteAsignaturaSource: RemoteAsignaturaSource by lazy {
        RemoteAsignaturaSource(httpClient)
    }

    private val remoteCarreraSource: RemoteCarreraSource by lazy {
        RemoteCarreraSource(httpClient)
    }

    private val remoteHorarioSource: RemoteHorarioSource by lazy {
        RemoteHorarioSource(httpClient)
    }

    private val remotePasswordSource: RemotePasswordSource by lazy {
        RemotePasswordSource(httpClient)
    }

    private val localAsignaturaSource: LocalAsignaturaSource by lazy {
        LocalAsignaturaSource()
    }

    private val cacheCarreraSource: CacheCarreraSource by lazy {
        CacheCarreraSource()
    }

    private val localHorarioSource: LocalHorarioSource by lazy {
        LocalHorarioSource()
    }

    val asesoriaRepository: AsesoriaRepository by lazy {
        AsesoriaRepositoryImpl(
            remoteAsesoriaSource = remoteAsesoriaSource,
            preferencesSource = localPreferencesSource
        )
    }

    val asignaturaRepository: AsignaturaRepository by lazy {
        AsignaturaRepositoryImpl(
            remoteAsignaturaSource = remoteAsignaturaSource,
            localAsignaturaSource = localAsignaturaSource
        )
    }

    val carreraRepository: CarreraRepository by lazy {
        CarreraRepositoryImpl(
            remoteCarreraSource = remoteCarreraSource,
            cacheCarreraSource = cacheCarreraSource
        )
    }

    val horarioRepository: HorarioRepository by lazy {
        HorarioRepositoryImpl(
            remoteHorarioSource = remoteHorarioSource,
            localHorarioSource = localHorarioSource,
            preferencesSource = localPreferencesSource
        )
    }

    val passwordRepository: PasswordRepository by lazy {
        PasswordRepositoryImpl(
            passwordSource = remotePasswordSource
        )
    }

    val estudianteRepository: EstudianteRepository by lazy {
        EstudianteRepositoryImpl(
            remoteEstudianteSource = remoteEstudianteSource,
            localEstudianteSource = localEstudianteSource,
            preferencesSource = localPreferencesSource
        )
    }

    val loginRepository: LoginRepository by lazy {
        LoginRepositoryImpl(
            remoteTokenSource = remoteTokenSource,
            localPreferencesSource = localPreferencesSource,
            estudianteRepository = estudianteRepository
        )
    }
}
