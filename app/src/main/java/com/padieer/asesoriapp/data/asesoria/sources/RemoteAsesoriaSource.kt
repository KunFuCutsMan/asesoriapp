package com.padieer.asesoriapp.data.asesoria.sources

import androidx.datastore.core.IOException
import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.error.mapDataNetworkError
import com.padieer.asesoriapp.domain.model.AsesoriaModel
import com.padieer.asesoriapp.domain.model.DataResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.appendPathSegments
import io.ktor.http.isSuccess
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format.char
import kotlinx.serialization.Serializable

class RemoteAsesoriaSource(
    private val client: HttpClient
) {

    @Serializable
    internal data class PostParams(
        val carreraID: Int,
        val asignaturaID: Int,
        val diaAsesoria: String,
        val horaInicial: String,
        val horaFinal: String,
    )

    suspend fun post(
        token: String,
        carreraID: Int,
        asignaturaID: Int,
        fecha: LocalDate,
        horaInicio: LocalTime,
        horaFinal: LocalTime
    ): Result<AsesoriaModel, DataError.Network> {
        try {
            val timeFormatter = LocalTime.Format { hour(); char(':'); minute() }
            val dateFormatter = LocalDate.Format { day(); char('-'); monthNumber(); char('-'); year() }

            val response = client.post {
                url {
                    appendPathSegments("api", "v1", "asesorias")
                }
                bearerAuth(token)
                setBody(PostParams(
                    carreraID = carreraID,
                    asignaturaID = asignaturaID,
                    diaAsesoria = dateFormatter.format(fecha),
                    horaInicial = timeFormatter.format(horaInicio),
                    horaFinal = timeFormatter.format(horaFinal),
                ))
            }

            if (response.status.isSuccess()) {
                val body: DataResponse<AsesoriaModel> = response.body()
                return Result.Success(body.data)
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

    suspend fun fetch(
        token: String,
        estudianteID: Int? = null,
        asesorID: Int? = null,
    ): Result<List<AsesoriaModel>, DataError.Network> {
        try {
            val response = client.get {
                url {
                    appendPathSegments("api", "v1", "asesorias")
                    estudianteID?.let { parameter("estudianteID", it) }
                    asesorID?.let { parameter("asesorID", it) }
                }
                bearerAuth(token)
            }

            if (response.status.isSuccess()) {
                val body: DataResponse<List<AsesoriaModel>> = response.body()
                return Result.Success(body.data)
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
}