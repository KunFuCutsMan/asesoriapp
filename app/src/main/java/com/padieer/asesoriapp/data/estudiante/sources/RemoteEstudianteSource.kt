package com.padieer.asesoriapp.data.estudiante.sources

import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class RemoteEstudianteSource(
    private val client: HttpClient,
    private val initialURL: String,
) {

    suspend fun insert(estudiante: InsertableEstudiante): Result<Unit, DataError.Network> {
        val response = client.post(urlString = initialURL) {
            url {
                appendPathSegments("api", "v1", "estudiante")
                port = 8000
            }
            contentType(ContentType.Application.Json)
            setBody(estudiante)
        }

        if (response.status.isSuccess())
            return Result.Success(Unit)

        return when(response.status.value) {
            302 -> Result.Error(DataError.Network.BAD_PARAMS)
            else -> Result.Error(DataError.Network.UNKWOWN)
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