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
    val carreraID: Int,
    val asesor: AsesorModel?
)