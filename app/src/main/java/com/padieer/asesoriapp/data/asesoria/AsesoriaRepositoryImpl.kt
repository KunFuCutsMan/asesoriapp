package com.padieer.asesoriapp.data.asesoria

import com.padieer.asesoriapp.crypto.LocalPreferencesSource
import com.padieer.asesoriapp.data.asesoria.sources.RemoteAsesoriaSource
import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.model.AsesoriaModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class AsesoriaRepositoryImpl(
    private val remoteAsesoriaSource: RemoteAsesoriaSource,
    private val preferencesSource: LocalPreferencesSource,
): AsesoriaRepository {
    override suspend fun postAsesoria(
        carreraID: Int,
        asignaturaID: Int,
        fecha: LocalDate,
        horaInicio: LocalTime,
        horaFinal: LocalTime
    ): Result<Unit, DataError.Network> {
        val tokenResult = preferencesSource.fetchToken()
        if (tokenResult is Result.Error)
            return Result.Error(DataError.Network.UNAUTHENTICATED)

        val token = (tokenResult as Result.Success).data

        val asesoriaResult = remoteAsesoriaSource.post(
            token = token,
            carreraID = carreraID,
            asignaturaID = asignaturaID,
            fecha = fecha,
            horaInicio = horaInicio,
            horaFinal = horaFinal
        )

        if (asesoriaResult is Result.Error)
            return Result.Error(asesoriaResult.error)

        return Result.Success(Unit)
    }

    override suspend fun fetchAsesoriasOfEstudiante(estudianteID: Int): Result<List<AsesoriaModel>, DataError> {
        val tokenResult = preferencesSource.fetchToken()
        if (tokenResult is Result.Error)
            return Result.Error(DataError.Network.UNAUTHENTICATED)

        val token = (tokenResult as Result.Success).data

        val asesorias = remoteAsesoriaSource.fetch(token, estudianteID = estudianteID)

        if (asesorias is Result.Success) {
            return Result.Success(asesorias.data)
        }

        return asesorias
    }

    override suspend fun fetchAsesoriasOfAsesor(asesorID: Int): Result<List<AsesoriaModel>, DataError> {
        val tokenResult = preferencesSource.fetchToken()
        if (tokenResult is Result.Error)
            return Result.Error(DataError.Network.UNAUTHENTICATED)

        val token = (tokenResult as Result.Success).data

        val asesorias = remoteAsesoriaSource.fetch(token, asesorID = asesorID)

        if (asesorias is Result.Success) {
            return Result.Success(asesorias.data)
        }

        return asesorias
    }
}