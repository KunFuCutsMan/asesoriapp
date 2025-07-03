package com.padieer.asesoriapp.data.carrera

import android.util.Log
import com.padieer.asesoriapp.data.carrera.sources.CacheCarreraSource
import com.padieer.asesoriapp.data.carrera.sources.RemoteCarreraSource

class CarreraRepositoryImpl(
    private val remoteCarreraSource: RemoteCarreraSource,
    private val cacheCarreraSource: CacheCarreraSource,
) : CarreraRepository {

    override suspend fun getCarreras(): List<CarreraModel> {
        if (cacheCarreraSource.carreras != null)
            return cacheCarreraSource.carreras!!

        val res = remoteCarreraSource.fetchCarreras()
        res.fold(
            onSuccess = {
                Log.d( "[DEBUG]", "Longitud de Carreras: ${it.size}")
                if (it.isEmpty())
                    return it.plus( CarreraModel(0, "", "") )

                cacheCarreraSource.setCarreras(it)
                return it
            },
            onFailure = {
                Log.d("[DEBUG]", "Got error: $it")
                Log.e("[DEBUG]", it.stackTraceToString())
                return listOf( CarreraModel(0, "", "") )
            }
        )
    }
}