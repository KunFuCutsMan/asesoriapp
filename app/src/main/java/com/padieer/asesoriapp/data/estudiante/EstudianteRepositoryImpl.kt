package com.padieer.asesoriapp.data.estudiante

import com.padieer.asesoriapp.data.carrera.CarreraRepository
import com.padieer.asesoriapp.data.estudiante.sources.RemoteEstudianteSource
import com.padieer.asesoriapp.crypto.LocalPreferencesSource
import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result

class EstudianteRepositoryImpl(
    private val remoteEstudianteSource: RemoteEstudianteSource,
    private val preferencesSource: LocalPreferencesSource,
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
    ): Result<Unit, DataError.Network> {
        val carreraID = carreraRepository.getCarreras().first { it.nombre == carrera }.id

        val result = remoteEstudianteSource.insert(
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

        return result
    }

    override suspend fun getEstudianteByToken(token: String): Result<EstudianteModel, DataError> {
        val localTokenRes = preferencesSource.fetchToken()
        if (localTokenRes is Result.Success && localTokenRes.data == token) {
            return preferencesSource.fetchCurrentEstudiante()
        }

        val remoteEstudianteRes = remoteEstudianteSource.fetchByToken(token)
        if (remoteEstudianteRes is Result.Success)
            preferencesSource.saveEstudiante(remoteEstudianteRes.data)

        return remoteEstudianteRes
    }
}