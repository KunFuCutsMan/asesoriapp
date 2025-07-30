package com.padieer.asesoriapp.data.carrera

import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.model.CarreraModel

interface CarreraRepository {
    suspend fun getCarreras(): Result<List<CarreraModel>, DataError>
}