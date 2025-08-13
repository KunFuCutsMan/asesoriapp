package com.padieer.asesoriapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AsesorModel (
    val id: Int,
    val estudianteID: Int,
    val admin: AdminModel? = null
)

data class Asesor(
    val id: Int,
)

fun AsesorModel.toUIModel() = Asesor(
    id = this.id
)