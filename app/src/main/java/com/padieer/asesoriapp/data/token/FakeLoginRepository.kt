package com.padieer.asesoriapp.data.token

import com.padieer.asesoriapp.App
import com.padieer.asesoriapp.data.estudiante.EstudianteModel
import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result

class FakeLoginRepository: LoginRepository {
    override suspend fun getToken(
        numControl: String,
        contrasena: String
    ): Result<String, DataError> {
        return Result.Success("")
    }

    override suspend fun getLoggedInUser(): Result<EstudianteModel, DataError> {
        return App.appModule.estudianteRepository.getEstudianteByToken("")
    }

    override suspend fun logOut(): Result<Unit, DataError.Local> {
        return Result.Success(Unit)
    }
}