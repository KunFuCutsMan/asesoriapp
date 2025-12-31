package com.padieer.asesoriapp.data.asesoria

import com.padieer.asesoriapp.crypto.LocalPreferencesSource
import com.padieer.asesoriapp.data.estudiante.EstudianteRepository
import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.model.AsesoriaModel
import com.padieer.asesoriapp.domain.model.EstudianteModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class AsesoriaRepositoryImpl(
    private val estudianteRepository: EstudianteRepository,
    private val preferencesSource: LocalPreferencesSource,
): AsesoriaRepository {
    override suspend fun postAsesoria(
        carreraID: Int,
        asignaturaID: Int,
        fecha: LocalDate,
        horaInicio: LocalTime,
        horaFinal: LocalTime
    ): Result<Unit, DataError.Network> {
        val tokenResult = preferencesSource.fetchToken()
        if (tokenResult is Result.Error)
            return Result.Error(DataError.Network.UNAUTHENTICATED)

        val token = (tokenResult as Result.Success).data

        // TODO: Implementar la lógica para postear una asesoría

        return Result.Success(Unit)
    }

    override suspend fun fetchAsesoriasOfEstudiante(estudianteID: Int): Result<List<AsesoriaModel>, DataError> {
        val tokenResult = preferencesSource.fetchToken()
        if (tokenResult is Result.Error)
            return Result.Error(DataError.Network.UNAUTHENTICATED)

        val token = (tokenResult as Result.Success).data

        // TODO: Implementar la lógica para obtener las asesorías de un estudiante

        return Result.Success(emptyList())
    }

    override suspend fun fetchAsesoriasOfAsesor(asesorID: Int): Result<List<AsesoriaModel>, DataError> {
        val tokenResult = preferencesSource.fetchToken()
        if (tokenResult is Result.Error)
            return Result.Error(DataError.Network.UNAUTHENTICATED)

        val token = (tokenResult as Result.Success).data

        // TODO: Implementar la lógica para obtener las asesorías de un asesor

        return Result.Success(emptyList())
    }

    override suspend fun getAsesores(): Result<List<EstudianteModel>, DataError> {
        return estudianteRepository.getAsesores()
    }
}