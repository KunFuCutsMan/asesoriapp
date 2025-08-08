package com.padieer.asesoriapp.data.horario

import com.padieer.asesoriapp.crypto.LocalPreferencesSource
import com.padieer.asesoriapp.data.horario.sources.LocalHorarioSource
import com.padieer.asesoriapp.data.horario.sources.RemoteHorarioSource
import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.model.HorarioModel

class HorarioRepositoryImpl(
    private val remoteHorarioSource: RemoteHorarioSource,
    private val localHorarioSource: LocalHorarioSource,
    private val preferencesSource: LocalPreferencesSource
): HorarioRepository {
    override suspend fun fetchHorarios(asesorID: Int): Result<List<HorarioModel>, DataError> {
        val localHorarios = localHorarioSource.get()
        if (localHorarios.isNotEmpty())
            return Result.Success(localHorarios)

        val tokenResult = preferencesSource.fetchToken()
        if (tokenResult is Result.Error) return Result.Error(tokenResult.error)
        val token = (tokenResult as Result.Success).data

        val horariosResult = remoteHorarioSource.fetch(token, asesorID)
        if (horariosResult is Result.Success)
            localHorarioSource.set(horariosResult.data)

        return horariosResult
    }

    override suspend fun updateHorarios(
        asesorID: Int,
        horarios: List<HorarioRepository.HorarioParams>
    ): Result<List<HorarioModel>, DataError> {
        val tokenResult = preferencesSource.fetchToken()
        if (tokenResult is Result.Error) return Result.Error(tokenResult.error)
        val token = (tokenResult as Result.Success).data

        val result = remoteHorarioSource.patch(
            token = token,
            asesorID = asesorID,
            horarios = horarios,
        )

        if (result is Result.Success)
            localHorarioSource.set(result.data)

        return result
    }
}