package com.padieer.asesoriapp.data.carrera

import android.util.Log
import com.padieer.asesoriapp.data.carrera.db.APIClient

class CarreraRepositoryImpl(
    private val client: APIClient
) : CarreraRepository {

    override suspend fun getCarreras(): List<CarreraModel> {

        val result = client.request<List<CarreraModel>>(
            segmentos = arrayListOf("carreras"),
            metodo = "GET",
        )

        var lista = emptyList<CarreraModel>()

        when {
            result.isSuccess -> {
                result.getOrNull()?.let {
                    Log.e("DEBUG", it.toString() )
                    lista = it.content
                }
            }
        }

        return lista
    }
}