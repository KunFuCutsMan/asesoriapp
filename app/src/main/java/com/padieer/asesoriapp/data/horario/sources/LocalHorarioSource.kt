package com.padieer.asesoriapp.data.horario.sources

import com.padieer.asesoriapp.domain.model.HorarioModel

class LocalHorarioSource {

    private var data: List<HorarioModel> = emptyList()

    fun set(newHorarios: List<HorarioModel>) {
        data = newHorarios
    }

    fun get(): List<HorarioModel> = data
}