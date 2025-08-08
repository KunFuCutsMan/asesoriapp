package com.padieer.asesoriapp.data.asignatura

import com.padieer.asesoriapp.data.asignatura.sources.LocalAsignaturaSource
import com.padieer.asesoriapp.data.asignatura.sources.RemoteAsignaturaSource
import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.model.AsignaturaModel

class AsignaturaRepositoryImpl(
    private val remoteAsignaturaSource: RemoteAsignaturaSource,
    private val localAsignaturaSource: LocalAsignaturaSource,
): AsignaturaRepository {
    override suspend fun fetchAsignaturas(carreraID: Int?): Result<List<AsignaturaModel>, DataError> {
        if (!localAsignaturaSource.empty()) {
            carreraID?.let {
                val asignaturas = localAsignaturaSource.all()
                    .filter { asignatura -> asignatura.carreras.any { it.id == carreraID } }
                return Result.Success(asignaturas)
            }
            return Result.Success(localAsignaturaSource.all())
        }

        val result = remoteAsignaturaSource.get(carreraID)
        if (result is Result.Success)
            localAsignaturaSource.set(result.data)

        return result
    }

    override suspend fun fetchAsignatura(asignaturaID: Int): Result<AsignaturaModel, DataError> {
        if (!localAsignaturaSource.empty()) {
            val asignatura = localAsignaturaSource.get(asignaturaID)
            return if (asignatura != null) Result.Success(asignatura)
            else Result.Error(DataError.Local.NOT_FOUND)
        }

        val result = remoteAsignaturaSource.getByID(asignaturaID)
        if (result is Result.Success)
            localAsignaturaSource.set( listOf(result.data) )
        return result
    }
}