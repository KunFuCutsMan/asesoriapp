package com.padieer.asesoriapp.domain.model

import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalTime

@Serializable
data class HorarioModel(
    val id: Int,
    val horaInicio: LocalTime,
    val disponible: Boolean,
    val diaSemana: DiaSemana,
    val asesor: AsesorModel,
)
