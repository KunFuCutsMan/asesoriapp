package com.padieer.asesoriapp.data.carrera

import com.padieer.asesoriapp.data.carrera.sources.FakeCarreraSource
import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.model.CarreraModel

class FakeCarreraRepository(
    private val source: FakeCarreraSource = FakeCarreraSource()
): CarreraRepository {
    override suspend fun getCarreras(): Result<List<CarreraModel>, DataError> {
        return Result.Success(source.fetchCarreras().getOrNull()!!)
    }

    override fun getCarreraByID(id: Int): Result<CarreraModel, DataError.Local> {
        val carrera = source.fetchCarreras().getOrNull()?.find { it.id == id }
        return if (carrera != null) Result.Success(carrera)
        else Result.Error(DataError.Local.NOT_FOUND)
    }
}