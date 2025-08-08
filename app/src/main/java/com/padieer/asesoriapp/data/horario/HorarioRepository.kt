package com.padieer.asesoriapp.data.horario

import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.model.HorarioModel
import kotlinx.datetime.LocalTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface HorarioRepository {

    suspend fun fetchHorarios(asesorID: Int): Result<List<HorarioModel>, DataError>

    suspend fun updateHorarios(asesorID: Int, horarios: List<HorarioParams>): Result<List<HorarioModel>, DataError>

    @Serializable
    data class HorarioParams(
        @SerialName("hora")
        val horaInicio: LocalTime,
        val disponible: Boolean,
        val diaSemanaID: Int,
    )
}