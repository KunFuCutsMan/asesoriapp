package com.padieer.asesoriapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CarreraModel (
    val id: Int,
    val nombre: String,
    val codigo: String,
    val especialidades: List<EspecialidadModel>? = null
)

data class Carrera(
    val nombre: String,
)

fun CarreraModel.toUIModel() = Carrera(
    nombre = this.nombre,
)