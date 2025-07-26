package com.padieer.asesoriapp.data.password

import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result

interface PasswordRepository {

    suspend fun sendPasswordResetRequest(
        numControl: String,
        numTelefono: String
    ): Result<Unit, DataError.Network>

    suspend fun verifyPasswordCode(
        numControl: String,
        numTelefono: String,
        codigo: String,
    ): Result<Unit, DataError.Network>

    suspend fun sendNewPassword(
        password: String,
        passwordConf: String,
        codigo: String
    ): Result<Unit, DataError.Network>
}