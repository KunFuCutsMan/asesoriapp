package com.padieer.asesoriapp.data.password

import com.padieer.asesoriapp.data.password.sources.PasswordToken
import com.padieer.asesoriapp.data.password.sources.RemotePasswordSource
import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result

class PasswordRepositoryImpl(
    private val passwordSource: RemotePasswordSource
): PasswordRepository {

    private lateinit var passwordToken: PasswordToken

    override suspend fun sendPasswordResetRequest(
        numControl: String,
        numTelefono: String
    ): Result<Unit, DataError.Network> {
        return passwordSource.requestCode(numControl = numControl, numTelefono = numTelefono)
    }

    override suspend fun verifyPasswordCode(
        numControl: String,
        numTelefono: String,
        codigo: String
    ): Result<Unit, DataError.Network> {
        val res = passwordSource.verifyCode(
            numControl = numControl,
            numTelefono = numTelefono,
            code = codigo
        )

        if (res is Result.Success) {
            this.passwordToken = res.data
            return Result.Success(Unit)
        }

        return Result.Error( (res as Result.Error).error )
    }

    override suspend fun sendNewPassword(
        password: String,
        passwordConf: String,
        codigo: String,
    ): Result<Unit, DataError.Network> {
        if (!this::passwordToken.isInitialized)
            return Result.Error(DataError.Network.BAD_PARAMS) // No token == No reset

        return passwordSource.resetPassword(
            code = codigo,
            password = password,
            passwordConf = passwordConf,
            token = this.passwordToken
        )
    }
}