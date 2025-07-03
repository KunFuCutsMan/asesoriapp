package com.padieer.asesoriapp.domain.validators

import com.padieer.asesoriapp.data.carrera.CarreraRepository

class ValidateCarreraUseCase(private val carrera: String, private val carreraRepository: CarreraRepository) {

    suspend fun execute(): ValidationResult {
        if (carrera.isBlank())
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Carrera no debe de ser vacío"
            )

        if (!carrera.isAlpha())
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Carrera debe contener letras"
            )

        // ¿Existe la carrera?
        carreraRepository.getCarreras().firstOrNull { it.nombre == carrera }
            ?: return ValidationResult(
                isSuccessful = false,
                errorMessage = "Carrera no existe"
            )

        return ValidationResult(isSuccessful = true)
    }
}