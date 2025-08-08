package com.padieer.asesoriapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DiaSemana(
    val id: Int,
    val nombre: String,
)
