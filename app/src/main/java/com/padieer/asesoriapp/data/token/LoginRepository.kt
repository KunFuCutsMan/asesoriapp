package com.padieer.asesoriapp.data.token

import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result

interface LoginRepository {
    suspend fun getToken(numControl: String, contrasena: String): Result<String, DataError>
}