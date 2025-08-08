package com.padieer.asesoriapp.data.asignatura

import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.model.AsignaturaModel

class AsignaturaRepositoryImpl: AsignaturaRepository {
    override suspend fun fetchAsignaturas(carreraID: Int?): Result<List<AsignaturaModel>, DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchAsignatura(asignaturaID: Int): Result<AsignaturaModel, DataError> {
        TODO("Not yet implemented")
    }
}