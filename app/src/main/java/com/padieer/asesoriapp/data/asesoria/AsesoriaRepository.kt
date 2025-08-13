package com.padieer.asesoriapp.data.asesoria

import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.model.AsesoriaModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

interface AsesoriaRepository {

    suspend fun postAsesoria(
        carreraID: Int, asignaturaID: Int, fecha: LocalDate, horaInicio: LocalTime, horaFinal: LocalTime
    ): Result<Unit, DataError.Network>

    suspend fun fetchAsesoriasOfEstudiante(): Result<List<AsesoriaModel>, DataError>
}