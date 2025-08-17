package com.padieer.asesoriapp.data.estudiante

import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.model.AsesorModel
import com.padieer.asesoriapp.domain.model.CarreraModel
import com.padieer.asesoriapp.domain.model.EspecialidadModel
import com.padieer.asesoriapp.domain.model.EstudianteModel

class FakeEstudianteRepository: EstudianteRepository {
    override suspend fun insertEstudiante(
        nombre: String,
        numeroControl: String,
        apellidoPaterno: String,
        apellidoMaterno: String,
        numeroTelefono: String,
        semestre: Int,
        contrasena: String,
        carreraID: Int
    ): Result<Unit, DataError.Network> {
        return Result.Success(Unit)
    }

    override suspend fun getEstudianteByToken(token: String): Result<EstudianteModel, DataError> {
        return Result.Success(
            EstudianteModel(
            id = 1,
            numeroControl = "20000001",
            nombre = "Juan",
            apellidoPaterno = "Camanei",
            apellidoMaterno = "Camanei",
            numeroTelefono = "1800002402",
            semestre = 6,
            carrera = CarreraModel(
                    id = 1,
                    nombre = "Administración",
                    codigo = "A",
                    especialidades = listOf(
                        EspecialidadModel(
                            id = 1,
                            carreraID = 1,
                            nombre = "Gestión de Negocios",
                        ),
                        EspecialidadModel(
                            id = 2,
                            carreraID = 1,
                            nombre = "Mercadotecnia y Negocios Internacionales",
                        ),
                    )
            ),
            especialidad = EspecialidadModel(
                id = 1,
                carreraID = 1,
                nombre = "Gestión de Negocios",
            ),
            asesor = AsesorModel(
                id = 1,
                estudianteID = 1,
            ),
        )
        )
    }

    override suspend fun getEstudianteByID(estudianteID: Int): Result<EstudianteModel, DataError> {
        return Result.Success(
            EstudianteModel(
                id = 1,
                numeroControl = "20000001",
                nombre = "Juan",
                apellidoPaterno = "Camanei",
                apellidoMaterno = "Camanei",
                numeroTelefono = "1800002402",
                semestre = 6,
                carrera = CarreraModel(
                    id = 1,
                    nombre = "Administración",
                    codigo = "A",
                    especialidades = listOf(
                        EspecialidadModel(
                            id = 1,
                            carreraID = 1,
                            nombre = "Gestión de Negocios",
                        ),
                        EspecialidadModel(
                            id = 2,
                            carreraID = 1,
                            nombre = "Mercadotecnia y Negocios Internacionales",
                        ),
                    )
                ),
                especialidad = EspecialidadModel(
                    id = 1,
                    carreraID = 1,
                    nombre = "Gestión de Negocios",
                ),
                asesor = AsesorModel(
                    id = 1,
                    estudianteID = 1,
                ),
            )
        )
    }
}