package com.padieer.asesoriapp.data.carrera

import android.util.Log
import com.padieer.asesoriapp.data.carrera.sources.CacheCarreraSource
import com.padieer.asesoriapp.data.carrera.sources.RemoteCarreraSource
import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result

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
}