package com.padieer.asesoriapp.domain.use_cases.asignatura

import com.padieer.asesoriapp.data.asignatura.AsignaturaRepository

class FetchAsignaturasUseCase(
    private val repository: AsignaturaRepository
) {
    suspend operator fun invoke(carreraID: Int? = null) = repository.fetchAsignaturas(carreraID)
}