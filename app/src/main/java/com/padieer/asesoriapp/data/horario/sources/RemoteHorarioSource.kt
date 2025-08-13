package com.padieer.asesoriapp.data.horario.sources

import androidx.datastore.core.IOException
import com.padieer.asesoriapp.data.horario.HorarioRepository
import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.error.mapDataNetworkError
import com.padieer.asesoriapp.domain.model.DataResponse
import com.padieer.asesoriapp.domain.model.HorarioModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.http.appendPathSegments
import io.ktor.http.isSuccess
import kotlinx.serialization.Serializable

class RemoteHorarioSource(
    private val client: HttpClient
) {
    suspend fun fetch(token: String, asesorID: Int): Result<List<HorarioModel>, DataError.Network> {
        try {
            val result = client.get {
                url {
                    appendPathSegments("api", "v1", "asesor", "$asesorID", "horario")
                }
                bearerAuth(token)
            }

            if (result.status.isSuccess()) {
                val body: DataResponse< List<HorarioModel> > = result.body()
                return Result.Success(body.data)
            }

            return mapDataNetworkError(result.status.value)
        }
        catch (e: Exception) {
            return when (e) {
                is SocketTimeoutException -> Result.Error(DataError.Network.TIMEOUT)
                is IOException -> Result.Error(DataError.Network.NO_CONNECTION)
                else -> Result.Error(DataError.Network.UNKWOWN)
            }
        }
    }

    suspend fun patch(
        token: String,
        asesorID: Int,
        horarios: List<HorarioRepository.HorarioParams>
    ): Result<List<HorarioModel>, DataError.Network> {
        try {
            val result = client.patch {
                url {
                    appendPathSegments("api", "v1", "asesor", "$asesorID", "horario")
                }
                setBody(HorarioParams(horas = horarios))
                bearerAuth(token)
            }

            if (result.status.isSuccess()) {
                val body: DataResponse<List<HorarioModel>> = result.body()
                return Result.Success(body.data)
            }

            return when (result.status.value) {
                302, 400, 422, -> Result.Error(DataError.Network.BAD_PARAMS)
                403 -> Result.Error(DataError.Network.FORBIDDEN)
                else -> Result.Error(DataError.Network.UNKWOWN)
            }
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
    private data class HorarioParams(
        val horas: List<HorarioRepository.HorarioParams>
    )
}