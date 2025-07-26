package com.padieer.asesoriapp.data.password

import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import kotlinx.coroutines.delay

class FakePasswordRepository: PasswordRepository {
    override suspend fun sendPasswordResetRequest(
        numControl: String,
        numTelefono: String
    ): Result<Unit, DataError.Network> {
        delay(3000L)
        return Result.Success(Unit)
    }

    override suspend fun sendNewPassword(
        password: String,
        passwordConf: String,
        codigo: String
    ): Result<Unit, DataError.Network> {
        delay(3000L)
        return if (password == passwordConf) Result.Success(Unit)
        else Result.Error(DataError.Network.BAD_PARAMS)
    }

    override suspend fun verifyPasswordCode(
        numControl: String,
        numTelefono: String,
        codigo: String
    ): Result<Unit, DataError.Network> {
        delay(3000L)
        return if (codigo == "123456") Result.Success(Unit)
        else Result.Error(DataError.Network.BAD_PARAMS)
    }
}