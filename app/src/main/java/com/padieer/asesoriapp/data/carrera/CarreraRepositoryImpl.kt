package com.padieer.asesoriapp.data.carrera

import android.util.Log
import com.padieer.asesoriapp.data.carrera.sources.CarreraSource

class CarreraRepositoryImpl(
    private val remoteCarreraSource: CarreraSource
) : CarreraRepository {

    override suspend fun getCarreras(): List<CarreraModel> {
        val res = remoteCarreraSource.fetchCarreras()
        res.fold(
            onSuccess = {
                Log.d( "[DEBUG]", "Longitud de Carreras: ${it.size}")
                if (it.isEmpty())
                    return it.plus( CarreraModel(0, "", "") )
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