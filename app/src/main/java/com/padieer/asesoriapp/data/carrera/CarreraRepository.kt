package com.padieer.asesoriapp.data.carrera

import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import kotlinx.serialization.Serializable

interface CarreraRepository {
    suspend fun getCarreras(): Result<List<CarreraModel>, DataError>
}

@Serializable
data class CarreraModel (
    val id: Int,
    val nombre: String,
    val codigo: String
)