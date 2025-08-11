package com.padieer.asesoriapp.domain.validators

import com.padieer.asesoriapp.data.carrera.CarreraRepository
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.error.ValidationError

class ValidateCarreraUseCase(private val carreraID: Int, private val carreraRepository: CarreraRepository) {

    suspend fun execute(): ValidationResult {
        // ¿Existe la carrera?
        return when (val res = carreraRepository.getCarreras()) {
            is Result.Success -> {
                res.data.firstOrNull { it.id == carreraID }
                    ?: return Result.Error(ValidationError.CarreraError.NOT_FOUND)

                Result.Success(Unit)
            }
            else -> Result.Error(ValidationError.CarreraError.NOT_FOUND)
        }
    }
}