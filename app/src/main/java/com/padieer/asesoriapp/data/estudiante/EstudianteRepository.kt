package com.padieer.asesoriapp.data.estudiante

import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import io.ktor.http.HttpStatusCode
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
    ): Result<Unit, DataError.Network>

    suspend fun getEstudianteByToken(token: String): Result<EstudianteModel, DataError>
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
    val carreraID: Int,
    val asesor: AsesorModel?
)

@Serializable
data class AsesorModel (
    val id: Int,
    val estudianteID: Int,
    val admin: AdminModel?
)

@Serializable
data class AdminModel (
    val id: Int,
    val asesorID: Int
)