package com.padieer.asesoriapp.domain.use_cases.asesoria.pedir_asesoria

import com.padieer.asesoriapp.data.asesoria.AsesoriaRepository
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class AgendarAsesoriaUseCase(
    private val repository: AsesoriaRepository
) {
    suspend operator fun invoke(carreraID: Int, asignaturaID: Int, fecha: LocalDate, horaInicio: LocalTime, horaFinal: LocalTime) = repository.postAsesoria(carreraID, asignaturaID, fecha, horaInicio, horaFinal)
}