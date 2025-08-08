package com.padieer.asesoriapp.domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalTime
import kotlinx.serialization.SerialName


@Serializable
data class HorarioModel(
    val id: Int,
    val horaInicio: LocalTime,
    val disponible: Boolean,
    val diaSemana: DiaSemana,
    val asesor: AsesorModel,
    @SerialName("created_at") val createdAt: LocalDateTime?,
    @SerialName("updated_at") val updatedAt: LocalDateTime?,
)
