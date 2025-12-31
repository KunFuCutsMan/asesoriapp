package com.padieer.asesoriapp.domain.use_cases.asesoria.terminar_asesoria

import com.padieer.asesoriapp.data.verificacion_asesoria.VerificacionAsesoriaRepository

class VerifyAsesoriaCompletionUseCase(
    private val repository: VerificacionAsesoriaRepository
) {
    suspend operator fun invoke(asesoriaId: Int, code: String) = repository.verifyAsesoriaCompletion(asesoriaId, code)
}