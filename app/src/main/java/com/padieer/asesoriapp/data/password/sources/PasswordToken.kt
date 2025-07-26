package com.padieer.asesoriapp.data.password.sources

import kotlinx.serialization.Serializable

@Serializable
data class PasswordToken(
    val token: String
)
