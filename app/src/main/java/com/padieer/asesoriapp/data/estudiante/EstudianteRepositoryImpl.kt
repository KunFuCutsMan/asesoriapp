package com.padieer.asesoriapp.data.estudiante

import android.util.Log
import com.padieer.asesoriapp.data.carrera.CarreraRepository
import com.padieer.asesoriapp.data.estudiante.sources.RemoteEstudianteSource
import io.ktor.http.isSuccess

class EstudianteRepositoryImpl(
    private val remoteEstudianteSource: RemoteEstudianteSource,
    private val carreraRepository: CarreraRepository
): EstudianteRepository {
    override suspend fun insertEstudiante(
        nombre: String,
        numeroControl: String,
        apellidoPaterno: String,
        apellidoMaterno: String,
        numeroTelefono: String,
        semestre: Int,
        contrasena: String,
        carrera: String
    ) {
        val carreraID = carreraRepository.getCarreras().first { it.nombre == carrera }.id

        val response = remoteEstudianteSource.insert(
            RemoteEstudianteSource.InsertableEstudiante(
                nombre = nombre,
                apellidoPaterno = apellidoPaterno,
                apellidoMaterno = apellidoMaterno,
                numeroControl = numeroControl,
                numeroTelefono = numeroTelefono,
                semestre = semestre,
                carreraID = carreraID,
                contrasena = contrasena,
                contrasenaConfirmation = contrasena,
            )
        )

        response.fold(
            onSuccess = {
                if (it.statusCode.isSuccess()) {
                    Log.i("ESTUDIANTE REPO", "Se creo el estudiante :D")
                }
                else {
                    it.body?.let { it1 -> Log.e("ESTUDIANTE REPO", it1) }
                }
            },
            onFailure = {
                Log.e("ESTUDIANTE REPO", it.toString())
            },
        )
    }
}