package com.padieer.asesoriapp.data.horario

import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.model.AsesorModel
import com.padieer.asesoriapp.domain.model.DiaSemana
import com.padieer.asesoriapp.domain.model.HorarioModel
import kotlinx.datetime.LocalTime

class FakeHorarioRepository: HorarioRepository {
    val horarios = listOf(
        HorarioModel(
            id = 1,
            horaInicio = LocalTime(7, 0, 0, 0),
            disponible = false,
            diaSemana = DiaSemana(1, "Lunes"),
            asesor = AsesorModel(1, 1, null),
            createdAt = null,
            updatedAt = null,
        ),
        HorarioModel(
            id = 2,
            horaInicio = LocalTime(8, 0, 0, 0),
            disponible = false,
            diaSemana = DiaSemana(1, "Lunes"),
            asesor = AsesorModel(1, 1, null),
            createdAt = null,
            updatedAt = null,
        ),
        HorarioModel(
            id = 1,
            horaInicio = LocalTime(14, 0, 0, 0),
            disponible = false,
            diaSemana = DiaSemana(2, "Martes"),
            asesor = AsesorModel(1, 1, null),
            createdAt = null,
            updatedAt = null,
        ),
    )

    override suspend fun fetchHorarios(asesorID: Int): Result<List<HorarioModel>, DataError> {
        return Result.Success(horarios)
    }

    override suspend fun updateHorarios(asesorID: Int, horarios: List<HorarioRepository.HorarioParams>): Result<List<HorarioModel>, DataError> {
        val nuevoHorarios = horarios.mapIndexed { index, horarioParam ->
            HorarioModel(
                id = index,
                horaInicio = horarioParam.horaInicio,
                disponible = horarioParam.disponible,
                diaSemana = DiaSemana(
                    id = horarioParam.diaSemanaID,
                    nombre = horarioParam.diaSemanaID.toDiaSemana()
                ),
                asesor = AsesorModel(1, 1, null),
                createdAt = null,
                updatedAt = null
            )
        }

        return Result.Success(nuevoHorarios)
    }
}

private fun Int.toDiaSemana(): String = when (this) {
    1 -> "Lunes"
    2 -> "Martes"
    3 -> "Miércoles"
    4 -> "Jueves"
    5 -> "Viernes"
    else -> "Sabado"
}