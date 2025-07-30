package com.padieer.asesoriapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AdminModel (
    val id: Int,
    val asesorID: Int
)