package com.padieer.asesoriapp.data.estudiante

import com.padieer.asesoriapp.data.Response
import kotlinx.serialization.Serializable

interface EstudianteRepository {

    suspend fun insertEstudiante(
        nombre: String,
        numeroControl: String,
        apellidoPaterno: String,
        apellidoMaterno: String,
        numeroTelefono: String,
        semestre: Int,
        contrasena: String,
        carrera: String,
    ): Result<Response>
}

@Serializable
data class EstudianteModel (
    val id: Int,
    val numeroControl: String,
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val numeroTelefono: String,
    val semestre: Int,
    val contrasena: String,
    val carreraID: Int,
)