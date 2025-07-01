package com.padieer.asesoriapp.data.carrera.db

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable

class APIClient {

    val URL = "https://10.0.2.2:8000/api/v1/"
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend inline fun <reified T> request(
        segmentos: List<String>,
        metodo: String,
        queryParams: Map<String, String>? = null,
        headers: Map<String, String>? = null,
        body: @Serializable Any? = null
    ): Result<APIResult<T>> {
        return runCatching {
            val response = client.request {
                method = HttpMethod(metodo)
                url {
                    URL
                    appendPathSegments(segmentos)
                    queryParams?.forEach{
                            (key, value) -> parameters.append(key, value)
                    }
                }
                headers {
                    let {
                        headers?.forEach { (key, value) -> it.append(key, value) }
                    }
                }
                let {
                    if (body != null) {
                        contentType(ContentType.Application.Json)
                        setBody(body)
                    }
                }
            }

            val content: T = response.body()
            APIResult(
                code = response.status.value,
                content = content
            )
        }
    }
}

data class APIResult<T>(
    val code: Int,
    val content: T
) {
    val isSuccessful = code in 200..299
    val isRedirected = code in 300..399
    val isClientError = code in 400..499
    val isServerError = code in 500..599
}