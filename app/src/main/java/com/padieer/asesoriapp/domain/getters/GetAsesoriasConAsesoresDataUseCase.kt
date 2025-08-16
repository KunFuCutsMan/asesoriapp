package com.padieer.asesoriapp.domain.getters

import com.padieer.asesoriapp.data.asesoria.AsesoriaRepository
import com.padieer.asesoriapp.data.estudiante.EstudianteRepository
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.model.Asesoria
import com.padieer.asesoriapp.domain.model.EstadoAsesoria
import com.padieer.asesoriapp.domain.model.Estudiante
import com.padieer.asesoriapp.domain.model.EstudianteModel
import com.padieer.asesoriapp.domain.model.toUIModel

class GetAsesoriasConAsesoresDataUseCase(
    private val estudianteRepository: EstudianteRepository,
    private val asesoriaRepository: AsesoriaRepository
) {

    suspend operator fun invoke(estudianteID: Int): List<AsesoriaConAsesorData> {
        val asesoriasResult = asesoriaRepository.fetchAsesoriasOfEstudiante(estudianteID)
        if (asesoriasResult is Result.Error)
            return emptyList()

        val asesorias = (asesoriasResult as Result.Success).data
        val asesores: MutableList<EstudianteModel?> = mutableListOf()
        asesorias.forEach {
            if (it.asesor != null) {
                val asesorData = estudianteRepository.getEstudianteByID(it.asesor.estudianteID)
                if (asesorData is Result.Success) {
                    asesores.add(asesorData.data)
                }
                else asesores.add(null)
            }
            else {
                asesores.add(null)
            }
        }

        return asesorias.mapIndexed { index, it ->
            AsesoriaConAsesorData(
                asesoria = it.toUIModel(),
                asesorData = asesores[index]?.toUIModel(),
                estadoAsesoria = it.estado
            )
        }
    }
}

data class AsesoriaConAsesorData(
    val asesoria: Asesoria,
    val asesorData: Estudiante?,
    val estadoAsesoria: EstadoAsesoria
)