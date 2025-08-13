package com.padieer.asesoriapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class EstudianteModel (
    val id: Int,
    val numeroControl: String,
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val numeroTelefono: String,
    val semestre: Int,
    val carrera: CarreraModel,
    val especialidad: EspecialidadModel? = null,
    val asesor: AsesorModel? = null
)

data class Estudiante(
    val nombre: String,
    val apePaterno: String,
    val apeMaterno: String,
    val numeroTelefono: String,
    val numeroControl: String,
    val semestre: Int,
    val asesor: Asesor?,
    val admin: Admin?,
)

fun EstudianteModel.toUIModel() = Estudiante(
    nombre = this.nombre,
    apePaterno = this.apellidoPaterno,
    apeMaterno = this.apellidoMaterno,
    numeroTelefono = this.numeroTelefono,
    numeroControl = this.numeroControl,
    semestre = this.semestre,
    asesor = this.asesor?.toUIModel(),
    admin = this.asesor?.admin?.toUIModel(),
)