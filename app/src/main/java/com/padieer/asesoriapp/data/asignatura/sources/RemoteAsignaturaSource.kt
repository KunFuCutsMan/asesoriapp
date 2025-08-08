package com.padieer.asesoriapp.data.asignatura.sources

import androidx.datastore.core.IOException
import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.model.AsignaturaModel
import com.padieer.asesoriapp.domain.model.DataResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.appendPathSegments
import io.ktor.http.isSuccess

class RemoteAsignaturaSource(
    private val client: HttpClient
) {
    suspend fun get(carreraID: Int?): Result<List<AsignaturaModel>, DataError.Network> {
        try {
            val response = client.get {
                url {
                    appendPathSegments("api", "v1", "asignaturas")
                    carreraID?.let {
                        parameter("carreraID", carreraID)
                    }
                }
            }

            if (response.status.isSuccess()) {
                val body: DataResponse<List<AsignaturaModel>> = response.body()
                return Result.Success(body.data)
            }

            return when (response.status.value) {
                302, 400, 422 -> Result.Error(DataError.Network.BAD_PARAMS)
                404 -> Result.Error(DataError.Network.NOT_FOUND)
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

    suspend fun getByID(asignaturaID: Int): Result<AsignaturaModel, DataError.Network> {
        try {
            val response = client.get {
                url {
                    appendPathSegments("api", "v1", "asignaturas", "$asignaturaID")
                }
            }

            if (response.status.isSuccess()) {
                val body: DataResponse<AsignaturaModel> = response.body()
                return Result.Success(body.data)
            }

            return when (response.status.value) {
                302, 400, 422 -> Result.Error(DataError.Network.BAD_PARAMS)
                404 -> Result.Error(DataError.Network.NOT_FOUND)
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
}