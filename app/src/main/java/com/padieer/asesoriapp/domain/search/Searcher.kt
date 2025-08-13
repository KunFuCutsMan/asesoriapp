package com.padieer.asesoriapp.domain.search

interface Searcher<T> {

    suspend fun init()

    suspend fun put(docs: List<T>): Boolean

    suspend fun query(query: String): List<T>

    fun closeSession()
}