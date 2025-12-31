package com.padieer.asesoriapp.domain.use_cases.asesoria.pedir_asesoria

import com.padieer.asesoriapp.data.asesoria.AsesoriaRepository

class GetAsesoresUseCase(
    private val repository: AsesoriaRepository
) {
    suspend operator fun invoke() = repository.getProfesores()
}