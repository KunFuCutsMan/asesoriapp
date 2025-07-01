package com.padieer.asesoriapp.data.carrera

import com.padieer.asesoriapp.data.carrera.sources.CarreraSource

class CarreraRepositoryImpl(
    private val remoteCarreraSource: CarreraSource
) : CarreraRepository {

    override suspend fun getCarreras(): List<CarreraModel> {

        return remoteCarreraSource.fetchCarreras()
    }
}