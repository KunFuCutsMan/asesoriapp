package com.padieer.asesoriapp.data.carrera

import kotlinx.serialization.Serializable

interface CarreraRepository {
    suspend fun getCarreras(): List<CarreraModel>
}

@Serializable
data class CarreraModel (
    val id: Int,
    val nombre: String,
    val codigo: String
)