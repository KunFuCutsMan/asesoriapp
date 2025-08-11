package com.padieer.asesoriapp.data.estudiante

import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.model.EstudianteModel

interface EstudianteRepository {

    suspend fun insertEstudiante(
        nombre: String,
        numeroControl: String,
        apellidoPaterno: String,
        apellidoMaterno: String,
        numeroTelefono: String,
        semestre: Int,
        contrasena: String,
        carreraID: Int,
    ): Result<Unit, DataError.Network>

    suspend fun getEstudianteByToken(token: String): Result<EstudianteModel, DataError>
}