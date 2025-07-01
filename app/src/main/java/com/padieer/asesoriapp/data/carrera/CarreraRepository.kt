package com.padieer.asesoriapp.data.carrera

interface CarreraRepository {
    suspend fun getCarreras(): List<CarreraModel>
}

data class CarreraModel (
    val id: Int,
    val nombre: String,
    val codigo: String
)