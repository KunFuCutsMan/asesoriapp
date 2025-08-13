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
    val estudiante: EstudianteModel,
    val asesor: AsesorModel?,
)

@Serializable
data class EstadoAsesoria(
    val id: Int,
    @SerialName("estado")
    val nombre: String
)

data class Asesoria(
    val id: Int,
    val dia: LocalDate,
    val horaInicio: LocalTime,
    val horaFinal: LocalTime,
    val carrera: Carrera,
    val asignatura: Asignatura,
    val estado: String,
    val estudiante: Estudiante,
    val asesor: Asesor?,
)

fun AsesoriaModel.toUIModel() = Asesoria(
    id = this.id,
    dia = this.dia,
    horaInicio = this.horaInicial,
    horaFinal = this.horaFinal,
    carrera = this.carrera.toUIModel(),
    asignatura = this.asignatura.toUIModel(),
    estado = this.estado.nombre,
    estudiante = this.estudiante.toUIModel(),
    asesor = this.asesor?.toUIModel(),
)
