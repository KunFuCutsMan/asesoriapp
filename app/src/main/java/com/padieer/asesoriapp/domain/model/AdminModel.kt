package com.padieer.asesoriapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AdminModel (
    val id: Int,
    val asesorID: Int
)

data class Admin(
    val id: Int
)

fun AdminModel.toUIModel() = Admin(
    id = this.id
)