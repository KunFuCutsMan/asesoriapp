package com.padieer.asesoriapp.data.asignatura

import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.model.AsignaturaModel

interface AsignaturaRepository {

    suspend fun fetchAsignaturas(carreraID: Int?): Result<List<AsignaturaModel>, DataError>

    suspend fun fetchAsignatura(asignaturaID: Int): Result<AsignaturaModel, DataError>
}