package com.padieer.asesoriapp.data.carrera

import com.padieer.asesoriapp.data.carrera.sources.CacheCarreraSource
import com.padieer.asesoriapp.data.carrera.sources.RemoteCarreraSource
import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.model.CarreraModel

class CarreraRepositoryImpl(
    private val remoteCarreraSource: RemoteCarreraSource,
    private val cacheCarreraSource: CacheCarreraSource,
) : CarreraRepository {

    override suspend fun getCarreras(): Result<List<CarreraModel>, DataError> {
        if (cacheCarreraSource.carreras != null)
            return Result.Success(cacheCarreraSource.carreras!!)

        return when (val res = remoteCarreraSource.fetchCarreras()) {
            is Result.Success -> {
                cacheCarreraSource.setCarreras(res.data)
                res
            }
            else -> { res }
        }
    }

    override fun getCarreraByID(id: Int): Result<CarreraModel, DataError.Local> {
        val carrera = cacheCarreraSource.carreras?.find { it.id == id }
        return if (carrera != null) Result.Success(carrera)
        else Result.Error(DataError.Local.NOT_FOUND)
    }
}