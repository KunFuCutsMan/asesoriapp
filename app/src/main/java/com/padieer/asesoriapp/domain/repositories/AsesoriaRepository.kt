package com.padieer.asesoriapp.domain.repositories

import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.model.AsesorModel
import com.padieer.asesoriapp.domain.model.AsignaturaModel

interface AsesoriaRepository {
    suspend fun generateVerificationCode(asesoriaId: Int): Result<String, DataError.Network>
    suspend fun verifyCompletion(asesoriaId: Int, codigoAsesor: String, codigoEstudiante: String): Result<Boolean, DataError.Network>

    suspend fun getAsignaturas(): Result<List<AsignaturaModel>, DataError.Network>

    suspend fun getAsesores(): Result<List<AsesorModel>, DataError.Network>

    suspend fun agendarAsesoria(idAsignatura: Int, idAsesor: Int, idEstudiante: Int, fecha: String): Result<Boolean, DataError.Network>
}