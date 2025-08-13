package com.padieer.asesoriapp.data.password.sources

import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.error.mapDataNetworkError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.request.headers
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.io.IOException
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class RemotePasswordSource(
    private val client: HttpClient,
) {
    @Serializable
    private data class RequestCodeParams (
        val numeroControl: String,
        val numeroTelefono: String
    )

    suspend fun requestCode(numControl: String, numTelefono: String): Result<Unit, DataError.Network> {
        val response = client.post {
            url {
                appendPathSegments("api", "v1", "password")
                port = 8000
            }
            contentType(ContentType.Application.Json)
            setBody(RequestCodeParams(
                numeroControl = numControl,
                numeroTelefono = numTelefono
            ))
        }

        if (response.status.isSuccess())
            return Result.Success(Unit)

        return mapDataNetworkError(response.status.value)
    }

    @Serializable
    private data class VerifyCodeParams(
        val numeroControl: String,
        val numeroTelefono: String,
        val code: String
    )

    suspend fun verifyCode(numControl: String, numTelefono: String, code: String): Result<PasswordToken, DataError.Network> {
        try {
        val response = client.post {
            url {
                appendPathSegments("api", "v1", "password", "verify")
                port = 8000
            }
            contentType(ContentType.Application.Json)
            setBody(VerifyCodeParams(
                numeroControl = numControl,
                numeroTelefono = numTelefono,
                code = code,
            ))
        }

        if (response.status.isSuccess()) {
            val token: PasswordToken = response.body()
            return Result.Success(token)
        }

        return mapDataNetworkError(response.status.value)
        }
        catch (e: Exception) {
            return when (e) {
                is SocketTimeoutException -> Result.Error(DataError.Network.TIMEOUT)
                is IOException -> Result.Error(DataError.Network.NO_CONNECTION)
                else -> Result.Error(DataError.Network.UNKWOWN)
            }
        }
    }

    @Serializable
    private data class ResetPasswordParams(
        val code: String,
        val contrasena: String,
        @SerialName("contrasena_confirmation")
        val contrasenaConfirmation: String,
    )

    suspend fun resetPassword(code: String, password: String, passwordConf: String, token: PasswordToken): Result<Unit, DataError.Network> {
        try {

        val response = client.patch {
            url {
                appendPathSegments("api", "v1", "password")
                port = 8000
            }
            headers {
                append(HttpHeaders.Authorization, "Bearer ${token.token}")
            }
            contentType(ContentType.Application.Json)
            setBody(ResetPasswordParams(
                code = code,
                contrasena = password,
                contrasenaConfirmation = passwordConf
            ))
        }

        if (response.status.isSuccess())
            return Result.Success(Unit)

        return mapDataNetworkError(response.status.value)
        }
        catch (e: Exception) {
            return when (e) {
                is SocketTimeoutException -> Result.Error(DataError.Network.TIMEOUT)
                is IOException -> Result.Error(DataError.Network.NO_CONNECTION)
                else -> Result.Error(DataError.Network.UNKWOWN)
            }
        }


    }
}