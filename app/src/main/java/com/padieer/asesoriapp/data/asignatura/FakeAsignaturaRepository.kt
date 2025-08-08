package com.padieer.asesoriapp.data.asignatura

import com.padieer.asesoriapp.data.carrera.sources.FakeCarreraSource
import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.model.AsignaturaModel

class FakeAsignaturaRepository: AsignaturaRepository {
    override suspend fun fetchAsignaturas(carreraID: Int?): Result<List<AsignaturaModel>, DataError> {
        return Result.Success( asignaturaFactory(15) )
    }

    override suspend fun fetchAsignatura(asignaturaID: Int): Result<AsignaturaModel, DataError> {
        return Result.Success( asignaturaFactory(1).first() )
    }

    companion object {
        fun asignaturaFactory(count: Int): List<AsignaturaModel> {
            return (1..count).map { cuenta ->
                AsignaturaModel(
                    id = cuenta,
                    nombre = "Asignatura $cuenta",
                    carreras = FakeCarreraSource.carreras().filter {count % it.id == 0}
                )
            }
        }
    }
}