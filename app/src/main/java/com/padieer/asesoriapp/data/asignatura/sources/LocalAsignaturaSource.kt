package com.padieer.asesoriapp.data.asignatura.sources

import com.padieer.asesoriapp.domain.model.AsignaturaModel

class LocalAsignaturaSource {

    private val asignaturasMap: MutableMap<Int, AsignaturaModel> = mutableMapOf()

    fun all() = asignaturasMap.values.toList().sortedBy { it.id }

    fun set(asignaturas: List<AsignaturaModel>) {
        asignaturas.forEach { asignaturasMap[it.id] = it }
    }

    fun get(asignaturaID: Int) = asignaturasMap[asignaturaID]

    fun empty() = asignaturasMap.isEmpty()
}