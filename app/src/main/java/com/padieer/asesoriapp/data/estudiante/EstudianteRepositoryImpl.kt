package com.padieer.asesoriapp.data.estudiante

import com.padieer.asesoriapp.data.carrera.CarreraRepository
import com.padieer.asesoriapp.data.estudiante.sources.RemoteEstudianteSource
import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result

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
}