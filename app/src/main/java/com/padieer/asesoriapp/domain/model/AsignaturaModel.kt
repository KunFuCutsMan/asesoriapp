package com.padieer.asesoriapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AsignaturaModel(
    val id: Int,
    val nombre: String,
    val carreras: List<CarreraModel>
)