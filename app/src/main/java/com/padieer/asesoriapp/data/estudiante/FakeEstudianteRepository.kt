package com.padieer.asesoriapp.data.estudiante

import com.padieer.asesoriapp.data.Response
import io.ktor.http.HttpStatusCode

class FakeEstudianteRepository: EstudianteRepository {
    override suspend fun insertEstudiante(
        nombre: String,
        numeroControl: String,
        apellidoPaterno: String,
        apellidoMaterno: String,
        numeroTelefono: String,
        semestre: Int,
        contrasena: String,
        carrera: String
    ): Result<Response> {
        return runCatching { Response( statusCode = HttpStatusCode.Created ) }
    }
}