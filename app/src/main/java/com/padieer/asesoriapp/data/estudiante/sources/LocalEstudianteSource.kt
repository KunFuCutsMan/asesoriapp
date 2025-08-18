package com.padieer.asesoriapp.data.estudiante.sources

import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.model.EstudianteModel

class LocalEstudianteSource {

    private val estudiantes: MutableMap<Int, EstudianteModel> = mutableMapOf()

    fun saveEstudiante(estudiante: EstudianteModel): Result<Unit, DataError.Local> {
        estudiantes.put(estudiante.id, estudiante)
        return Result.Success(Unit)
    }

    fun fetchEstudiante(estudianteID: Int): Result<EstudianteModel, DataError.Local> {
        val estudiante = estudiantes[estudianteID]
        return if (estudiante != null) Result.Success(estudiante) else Result.Error(DataError.Local.NOT_FOUND)
    }
}