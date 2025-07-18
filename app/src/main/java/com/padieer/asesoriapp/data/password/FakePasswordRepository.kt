package com.padieer.asesoriapp.data.password

import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result

class FakePasswordRepository: PasswordRepository {
    override fun sendPasswordResetRequest(
        numControl: String,
        numTelefono: String
    ): Result<Unit, DataError.Network> {
        return Result.Success(Unit)
    }

    override fun sendNewPassword(
        resetToken: String,
        password: String,
        passwordConf: String
    ): Result<Unit, DataError.Network> {
        return if (resetToken == "12345678")
            Result.Success(Unit)
        else Result.Error(DataError.Network.BAD_PARAMS)
    }
}