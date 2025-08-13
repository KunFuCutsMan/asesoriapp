package com.padieer.asesoriapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class EspecialidadModel(
    val id: Int,
    val nombre: String,
    val carreraID: Int,
)

data class Especialidad(
    val nombre: String,
)

fun EspecialidadModel.toUIModel() = Especialidad(
    nombre = this.nombre
)
