package com.padieer.asesoriapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DataResponse<out T>(
    val data: T,
)
