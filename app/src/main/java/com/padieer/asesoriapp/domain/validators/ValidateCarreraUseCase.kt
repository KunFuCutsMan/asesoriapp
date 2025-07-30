package com.padieer.asesoriapp.domain.validators

import com.padieer.asesoriapp.data.carrera.CarreraRepository
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.error.ValidationError

class ValidateCarreraUseCase(private val carrera: String, private val carreraRepository: CarreraRepository) {

    suspend fun execute(): ValidationResult {
        if (carrera.isBlank())
            return Result.Error(ValidationError.CarreraError.NOT_EMPTY)

        if (!carrera.isAlpha())
            return Result.Error(ValidationError.CarreraError.NOT_ALPHA)

        // ¿Existe la carrera?
        return when (val res = carreraRepository.getCarreras()) {
            is Result.Success -> {
                res.data.firstOrNull { it.nombre == carrera }
                    ?: return Result.Error(ValidationError.CarreraError.NOT_FOUND)

                Result.Success(Unit)
            }
            else -> Result.Error(ValidationError.CarreraError.NOT_FOUND)
        }
    }
}