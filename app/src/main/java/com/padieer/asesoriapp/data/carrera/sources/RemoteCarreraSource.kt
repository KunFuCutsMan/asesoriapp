package com.padieer.asesoriapp.data.carrera.sources

import com.padieer.asesoriapp.data.carrera.CarreraModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments

class RemoteCarreraSource(
    private val client: HttpClient,
    private val initialURL: String,
) : CarreraSource {
    override suspend fun fetchCarreras(): Result<List<CarreraModel>> {
        return runCatching {
            val response = client.get( urlString = initialURL ) {
                url { urlBuilder ->
                    urlBuilder.appendPathSegments( "api", "v1", "carreras")
                    urlBuilder.port = 8000
                }
            }
            val listas = response.body<List<CarreraModel>>()
            listas
        }
    }
}