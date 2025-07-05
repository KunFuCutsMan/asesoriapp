package com.padieer.asesoriapp.data.token.sources

import com.padieer.asesoriapp.data.Response
import com.padieer.asesoriapp.data.toResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

class RemoteTokenSource(
    private val client: HttpClient,
    private val initialURL: String,
) {

    suspend fun fetchToken(numControl: String, contrasena: String): Result<Response> {
        return runCatching {
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
            response.toResponse()
        }
    }

    @Serializable
    internal data class TokenParams(
        val numeroControl: String,
        val contrasena: String
    )
}