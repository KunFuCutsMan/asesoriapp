package com.padieer.asesoriapp.data.password

import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result

interface PasswordRepository {

    fun sendPasswordResetRequest(
        numControl: String,
        numTelefono: String
    ): Result<Unit, DataError.Network>

    fun sendNewPassword(
        resetToken: String,
        password: String,
        passwordConf: String
    ): Result<Unit, DataError.Network>
}