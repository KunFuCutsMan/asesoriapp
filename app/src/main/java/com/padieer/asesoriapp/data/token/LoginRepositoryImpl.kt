package com.padieer.asesoriapp.data.token

import android.util.Log
import com.padieer.asesoriapp.crypto.LocalPreferencesSource
import com.padieer.asesoriapp.data.estudiante.EstudianteModel
import com.padieer.asesoriapp.data.estudiante.EstudianteRepository
import com.padieer.asesoriapp.data.token.sources.RemoteTokenSource
import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result

class LoginRepositoryImpl(
    private val remoteTokenSource: RemoteTokenSource,
    private val localPreferencesSource: LocalPreferencesSource,
    private val estudianteRepository: EstudianteRepository
): LoginRepository {
    override suspend fun getToken(numControl: String, contrasena: String): Result<String, DataError.Network> {
        // Check if there's one locally
        val localResult = localPreferencesSource.fetchToken()
        if (localResult is Result.Success)
            return Result.Success(localResult.data)

        // Get the remote version
        val remoteResult = remoteTokenSource.fetchToken(numControl, contrasena)
        if ( remoteResult is Result.Success )
            localPreferencesSource.saveToken(remoteResult.data)

        return remoteResult
    }

    override suspend fun getLoggedInUser(): Result<EstudianteModel, DataError> {
        // Revisa si hay uno guardado
        val localUserRes = localPreferencesSource.fetchCurrentEstudiante()
        if (localUserRes is Result.Success)
            return localUserRes

        // Si no, obtenlo por el token que existe
        return when (val localTokenRes = localPreferencesSource.fetchToken()) {
            is Result.Success -> {
                val token = localTokenRes.data
                estudianteRepository.getEstudianteByToken(token)
            }
            else -> { Result.Error(DataError.Local.NOT_FOUND) }
        }
    }

    override suspend fun logOut(): Result<Unit, DataError.Local> {
        return localPreferencesSource.deletePreferences()
    }
}