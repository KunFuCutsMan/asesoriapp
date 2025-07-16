package com.padieer.asesoriapp.data.carrera

import com.padieer.asesoriapp.data.carrera.sources.FakeCarreraSource

class FakeCarreraRepository(
    private val source: FakeCarreraSource = FakeCarreraSource()
): CarreraRepository {
    override suspend fun getCarreras(): List<CarreraModel> {
        return source.fetchCarreras().getOrNull()!!
    }
}