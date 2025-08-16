package com.padieer.asesoriapp.data.asesoria

import com.padieer.asesoriapp.data.asignatura.FakeAsignaturaRepository
import com.padieer.asesoriapp.data.estudiante.FakeEstudianteRepository
import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.model.AsesoriaModel
import com.padieer.asesoriapp.domain.model.EstadoAsesoria
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

class FakeAsesoriaRepository: AsesoriaRepository {

    private val asignaturaRepo = FakeAsignaturaRepository()
    private val estudianteRepo = FakeEstudianteRepository()

    override suspend fun postAsesoria(
        carreraID: Int,
        asignaturaID: Int,
        fecha: LocalDate,
        horaInicio: LocalTime,
        horaFinal: LocalTime
    ): Result<Unit, DataError.Network> {
        return Result.Success(Unit)
    }

    @OptIn(ExperimentalTime::class)
    override suspend fun fetchAsesoriasOfEstudiante(estudianteID: Int): Result<List<AsesoriaModel>, DataError> {
        val now = Clock.System.now()
        val asignaturas = (asignaturaRepo.fetchAsignaturas(1) as Result.Success).data
        val estudiante = (estudianteRepo.getEstudianteByToken("") as Result.Success).data

        val lista = asignaturas.mapIndexed { index, model ->
            val datetime = now.plus(Duration.parseIsoString("PT${24*(index+1)}H") ).toLocalDateTime(TimeZone.currentSystemDefault())
            val carrera = model.carreras!!.first()
            AsesoriaModel(
                id = index,
                dia = datetime.date,
                horaInicial = datetime.time,
                horaFinal = LocalTime( datetime.hour + 1, datetime.minute, datetime.second ),
                carrera = carrera,
                asignatura = model,
                estado = EstadoAsesoria( 3 % (index+1), "Algun Estado" ),
                estudiante = estudiante,
                asesor = null,
            )
        }

        return Result.Success(lista)
    }

    override suspend fun fetchAsesoriasOfAsesor(asesorID: Int): Result<List<AsesoriaModel>, DataError> {
        return this.fetchAsesoriasOfEstudiante(asesorID)
    }
}