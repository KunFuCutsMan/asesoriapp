package com.padieer.asesoriapp.data.asesoria

import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class FakeAsesoriaRepository: AsesoriaRepository {
    override suspend fun postAsesoria(
        carreraID: Int,
        asignaturaID: Int,
        fecha: LocalDate,
        horaInicio: LocalTime,
        horaFinal: LocalTime
    ): Result<Unit, DataError.Network> {
        return Result.Success(Unit)
    }
}