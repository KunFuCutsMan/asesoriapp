package com.padieer.asesoriapp.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AsesoriaModel(
    val id: Int,
    @SerialName("diaAsesoria")
    val dia: LocalDate,
    val horaInicial: LocalTime,
    val horaFinal: LocalTime,
    val carrera: CarreraModel,
    val asignatura: AsignaturaModel,
    @SerialName("estadoAsesoria")
    val estado: EstadoAsesoria,
    val estudianteID: Int,
    val asesor: AsesorModel?,
)

@Serializable
data class EstadoAsesoria(
    val id: Int,
    @SerialName("estado")
    val nombre: String
)
