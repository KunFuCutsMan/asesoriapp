package com.padieer.asesoriapp.data.token

import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result

class FakeLoginRepository: LoginRepository {
    override suspend fun getToken(
        numControl: String,
        contrasena: String
    ): Result<String, DataError> {
        return Result.Success("")
    }
}