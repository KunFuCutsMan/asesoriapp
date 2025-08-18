package com.padieer.asesoriapp.data.estudiante

import com.padieer.asesoriapp.crypto.LocalPreferencesSource
import com.padieer.asesoriapp.data.estudiante.sources.LocalEstudianteSource
import com.padieer.asesoriapp.data.estudiante.sources.RemoteEstudianteSource
import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.model.EstudianteModel

class EstudianteRepositoryImpl(
    private val remoteEstudianteSource: RemoteEstudianteSource,
    private val localEstudianteSource: LocalEstudianteSource,
    private val preferencesSource: LocalPreferencesSource,
): EstudianteRepository {
    override suspend fun insertEstudiante(
        nombre: String,
        numeroControl: String,
        apellidoPaterno: String,
        apellidoMaterno: String,
        numeroTelefono: String,
        semestre: Int,
        contrasena: String,
        carreraID: Int
    ): Result<Unit, DataError.Network> {
        val result = remoteEstudianteSource.insert(
            RemoteEstudianteSource.InsertableEstudiante(
                nombre = nombre,
                apellidoPaterno = apellidoPaterno,
                apellidoMaterno = apellidoMaterno,
                numeroControl = numeroControl,
                numeroTelefono = numeroTelefono,
                semestre = semestre,
                carreraID = carreraID,
                contrasena = contrasena,
                contrasenaConfirmation = contrasena,
            )
        )

        return result
    }

    override suspend fun getEstudianteByToken(token: String): Result<EstudianteModel, DataError> {
        val localTokenRes = preferencesSource.fetchToken()
        if (localTokenRes is Result.Success && localTokenRes.data == token) {
            val estudianteRes = preferencesSource.fetchCurrentEstudiante()
            if (estudianteRes is Result.Error) {
                preferencesSource.deletePreferences() // probablemente sea mejor que lo eliminemos
            }
            else return estudianteRes
        }

        val remoteEstudianteRes = remoteEstudianteSource.fetchByToken(token)
        if (remoteEstudianteRes is Result.Success) {
            preferencesSource.saveToken(token)
            preferencesSource.saveEstudiante(remoteEstudianteRes.data)
        }

        return remoteEstudianteRes
    }

    override suspend fun getEstudianteByID(estudianteID: Int): Result<EstudianteModel, DataError> {

        val localEstudiante = localEstudianteSource.fetchEstudiante(estudianteID)
        if (localEstudiante is Result.Success)
            return localEstudiante

        val tokenResult = preferencesSource.fetchToken()
        if (tokenResult is Result.Error)
            return Result.Error(DataError.Network.UNAUTHENTICATED)
        val token = (tokenResult as Result.Success).data

        val remoteEstudianteResult = remoteEstudianteSource.fetch(token, estudianteID)
        if (remoteEstudianteResult is Result.Success)
            localEstudianteSource.saveEstudiante(remoteEstudianteResult.data)

        return remoteEstudianteResult
    }
}