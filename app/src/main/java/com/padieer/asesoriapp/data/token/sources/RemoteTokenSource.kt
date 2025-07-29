package com.padieer.asesoriapp.data.token.sources

import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.serialization.Serializable

class RemoteTokenSource(
    private val client: HttpClient,
    private val initialURL: String,
) {

    suspend fun fetchToken(numControl: String, contrasena: String): Result<String, DataError.Network> {
        val response = client.post(urlString = initialURL) {
            url {
                appendPathSegments("api", "v1", "sanctum", "token")
                port = 8000
            }
            contentType(ContentType.Application.Json)
            setBody(TokenParams(
                    numeroControl = numControl,
                    contrasena = contrasena
                ))
        }

        if (response.status.isSuccess()) {
            val token: TokenResponse = response.body()
            return Result.Success(token.token)
        }

        return when (response.status.value) {
            302, 400, 422 -> Result.Error(DataError.Network.BAD_PARAMS)
            else -> Result.Error(DataError.Network.UNKWOWN)
        }
    }

    @Serializable
    private data class TokenParams(
        val numeroControl: String,
        val contrasena: String
    )

    @Serializable
    private data class TokenResponse(
        val token: String
    )
}