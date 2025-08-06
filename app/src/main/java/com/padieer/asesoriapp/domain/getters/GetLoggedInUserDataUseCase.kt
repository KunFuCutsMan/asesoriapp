package com.padieer.asesoriapp.domain.getters

import com.padieer.asesoriapp.data.carrera.CarreraRepository
import com.padieer.asesoriapp.data.token.LoginRepository
import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.model.AdminModel
import com.padieer.asesoriapp.domain.model.AsesorModel
import com.padieer.asesoriapp.domain.model.CarreraModel
import com.padieer.asesoriapp.domain.model.EstudianteModel

class GetLoggedInUserDataUseCase(
    private val loginRepository: LoginRepository,
) {

    data class EstudianteData(
        val estudiante: EstudianteModel,
        val carrera: CarreraModel,
        val asesor: AsesorModel?,
        val admin: AdminModel?
    )

    suspend operator fun invoke(): Result<EstudianteData, DataError> {
        val estudianteRes = loginRepository.getLoggedInUser()
        if (estudianteRes is Result.Error) return Result.Error(estudianteRes.error)
        val estudiante = (estudianteRes as Result.Success).data

        return Result.Success(EstudianteData(
            estudiante = estudiante,
            carrera = estudiante.carrera,
            asesor = estudiante.asesor,
            admin = estudiante.asesor?.admin,
        ))
    }
}