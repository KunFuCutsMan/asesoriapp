package com.padieer.asesoriapp.data.carrera.sources

import com.padieer.asesoriapp.data.carrera.CarreraModel

interface CarreraSource {

    suspend fun fetchCarreras(): List<CarreraModel>
}