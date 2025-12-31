package com.padieer.asesoriapp.data.verificacion_asesoria

import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result

interface VerificacionAsesoriaRepository {
    suspend fun generateVerificationCode(asesoriaId: Int): Result<String, DataError>
    suspend fun verifyAsesoriaCompletion(asesoriaId: Int, code: String): Result<Unit, DataError>
}