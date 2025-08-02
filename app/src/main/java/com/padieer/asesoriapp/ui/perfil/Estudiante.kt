package com.padieer.asesoriapp.ui.perfil

import com.padieer.asesoriapp.domain.model.CarreraModel
import com.padieer.asesoriapp.domain.model.EstudianteModel

data class Estudiante(
    val id: Int,
    val nombre: String,
    val apePaterno: String,
    val apeMaterno: String,
    val numeroTelefono: String,
    val numeroControl: String,
    val semestre: Int,
)

data class Carrera(
    val id: Int,
    val nombre: String,
)

fun CarreraModel.toUIModel() = Carrera(
    id = this.id,
    nombre = this.nombre
)

fun EstudianteModel.toUIModel() = Estudiante(
        id = this.id,
        nombre = this.nombre,
        apePaterno = this.apellidoPaterno,
        apeMaterno = this.apellidoMaterno,
        numeroTelefono = this.numeroTelefono,
        numeroControl = this.numeroControl,
        semestre = this.semestre,
    )
