package com.padieer.asesoriapp.data.carrera

import com.padieer.asesoriapp.data.carrera.sources.FakeCarreraSource
import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result

class FakeCarreraRepository(
    private val source: FakeCarreraSource = FakeCarreraSource()
): CarreraRepository {
    override suspend fun getCarreras(): Result<List<CarreraModel>, DataError> {
        return Result.Success(source.fetchCarreras().getOrNull()!!)
    }
}