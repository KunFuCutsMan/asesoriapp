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

    override suspend fun getEstudianteByToken(token: String): Result<EstudianteModel, DataError> {
        return Result.Success(EstudianteModel(
            id = 1,
            numeroControl = "20000001",
            nombre = "Juan",
            apellidoPaterno = "Camanei",
            apellidoMaterno = "Camanei",
            numeroTelefono = "1800002402",
            semestre = 6,
            carreraID = 1,
            asesor = null
        ))
    }
}