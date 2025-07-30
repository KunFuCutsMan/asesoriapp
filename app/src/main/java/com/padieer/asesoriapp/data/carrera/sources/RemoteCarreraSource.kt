package com.padieer.asesoriapp.data.carrera.sources

import com.padieer.asesoriapp.data.carrera.CarreraModel
import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import io.ktor.http.isSuccess
import kotlinx.io.IOException

class RemoteCarreraSource(
    private val client: HttpClient
) {
    suspend fun fetchCarreras(): Result<List<CarreraModel>, DataError.Network> {
        try {
            val response = client.get {
                url { urlBuilder ->
                    urlBuilder.appendPathSegments( "api", "v1", "carreras")
                    urlBuilder.port = 8000
                }
            }

            if (!response.status.isSuccess()) {
                return when (response.status.value) {

                    else -> Result.Error(DataError.Network.UNKWOWN)
                }
            }

            val listas: List<CarreraModel> = response.body()
            if (listas.isEmpty())
                return Result.Error(DataError.Network.NOT_FOUND)

            return Result.Success(listas)
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