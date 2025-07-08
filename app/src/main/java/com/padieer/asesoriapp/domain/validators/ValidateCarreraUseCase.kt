package com.padieer.asesoriapp.domain.validators

import com.padieer.asesoriapp.data.carrera.CarreraRepository
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.error.ValidationError

class ValidateCarreraUseCase(private val carrera: String, private val carreraRepository: CarreraRepository) {

    suspend fun execute(): Result<Unit, ValidationError.CarreraError> {
        if (carrera.isBlank())
            return Result.Error(ValidationError.CarreraError.NOT_EMPTY)

        if (!carrera.isAlpha())
            return Result.Error(ValidationError.CarreraError.NOT_ALPHA)

        // ¿Existe la carrera?
        carreraRepository.getCarreras().firstOrNull { it.nombre == carrera }
            ?: return Result.Error(ValidationError.CarreraError.NOT_FOUND)

        return Result.Success(Unit)
    }
}