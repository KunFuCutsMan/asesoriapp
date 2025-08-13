package com.padieer.asesoriapp.data.asignatura

import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.model.SearchableAsignatura
import com.padieer.asesoriapp.domain.model.toSearchable
import com.padieer.asesoriapp.domain.search.Searcher

class FakeAsignaturaSearcher(
    private val asignaturaRepository: AsignaturaRepository
): Searcher<SearchableAsignatura> {

    override suspend fun init() {
        // Listo :D
    }

    override suspend fun put(docs: List<SearchableAsignatura>): Boolean {
        return true
    }

    override suspend fun query(query: String): List<SearchableAsignatura> {
        return when (val res = asignaturaRepository.fetchAsignaturas(null)) {
            is Result.Success -> {
                return res.data
                    .filter { it.nombre.contains(query, true) }
                    .map { it.toSearchable() }
            }
            else -> { emptyList() }
        }
    }

    override fun closeSession() {
        // Listo :D
    }
}