package com.padieer.asesoriapp.domain.validators

import com.padieer.asesoriapp.data.asignatura.AsignaturaRepository
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.error.ValidationError

class ValidateAsignaturaUseCase(
    private val asignaturaID: Int, private val asignaturaRepository: AsignaturaRepository
) {

    suspend fun execute(): ValidationResult {

        if (asignaturaID <= 0)
            return Result.Error(ValidationError.AsignaturaError.NOT_VALID)

        val asignaturaResult = asignaturaRepository.fetchAsignatura(asignaturaID)
        if (asignaturaResult is Result.Error)
            return Result.Error(ValidationError.AsignaturaError.NOT_FOUND)

        return Result.Success(Unit)
    }
}