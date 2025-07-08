package com.padieer.asesoriapp.data.estudiante

import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result

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
    ): Result<Unit, DataError.Network> {
        return Result.Success(Unit)
    }
}