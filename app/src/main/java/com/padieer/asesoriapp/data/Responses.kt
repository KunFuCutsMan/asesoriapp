package com.padieer.asesoriapp.data

import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.json.Json

data class Response(
    val statusCode: HttpStatusCode,
    val body: String? = null
)

suspend fun HttpResponse.toResponse(): Response {
    val text = this.bodyAsText()
    return Response(
        statusCode = this.status,
        body = text,
    )
}

inline fun <reified T> Response.parseBody(): T {
    return Json.decodeFromString<T>(this.body!!)
}