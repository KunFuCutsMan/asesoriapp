package com.padieer.asesoriapp.data.estudiante.sources

import com.padieer.asesoriapp.data.Response
import com.padieer.asesoriapp.data.toResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class RemoteEstudianteSource(
    private val client: HttpClient,
    private val initialURL: String,
) {

    suspend fun insert(estudiante: InsertableEstudiante): Result<Response> {
        return runCatching {
            val response = client.post(urlString = initialURL) {
                url {
                    appendPathSegments("api", "v1", "estudiante")
                    port = 8000
                }
                contentType(ContentType.Application.Json)
                setBody(estudiante)
            }

            response.toResponse()
        }
    }

    @Serializable
    data class InsertableEstudiante (
        val numeroControl: String,
        val nombre: String,
        val apellidoPaterno: String,
        val apellidoMaterno: String,
        val semestre: Int,
        val numeroTelefono: String,
        val carreraID: Int,
        val contrasena: String,
        @SerialName("contrasena_confirmation")
        val contrasenaConfirmation: String,
    )
}