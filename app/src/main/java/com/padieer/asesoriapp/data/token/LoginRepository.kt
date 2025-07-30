package com.padieer.asesoriapp.data.token

import com.padieer.asesoriapp.domain.model.EstudianteModel
import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result

interface LoginRepository {
    suspend fun getToken(numControl: String, contrasena: String): Result<String, DataError>

    suspend fun getLoggedInUser(): Result<EstudianteModel, DataError>

    suspend fun logOut(): Result<Unit, DataError.Local>
}