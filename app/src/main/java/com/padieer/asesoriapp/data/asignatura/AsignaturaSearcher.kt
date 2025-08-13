package com.padieer.asesoriapp.data.asignatura

import android.content.Context
import androidx.appsearch.app.AppSearchSession
import androidx.appsearch.app.PutDocumentsRequest
import androidx.appsearch.app.SearchSpec
import androidx.appsearch.app.SetSchemaRequest
import androidx.appsearch.localstorage.LocalStorage
import com.padieer.asesoriapp.domain.model.SearchableAsignatura
import com.padieer.asesoriapp.domain.search.Searcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AsignaturaSearcher(
    private val context: Context
): Searcher<SearchableAsignatura> {

    private var session: AppSearchSession? = null

    override suspend fun init() {
        withContext(Dispatchers.IO) {
            val sessionFuture = LocalStorage.createSearchSessionAsync(
                LocalStorage.SearchContext.Builder(
                    context,
                    "asesorias"
                ).build()
            )

            val setSquemaRequest = SetSchemaRequest.Builder()
                .addDocumentClasses(SearchableAsignatura::class.java)
                .build()

            session = sessionFuture.get()
            session?.setSchemaAsync(setSquemaRequest)
        }
    }

    override suspend fun put(docs: List<SearchableAsignatura>): Boolean {
        return withContext(Dispatchers.IO) {
            session?.putAsync(PutDocumentsRequest.Builder()
                    .addDocuments(docs)
                    .build()
            )?.get()?.isSuccess == true
        }
    }

    override suspend fun query(query: String): List<SearchableAsignatura> {
        return withContext(Dispatchers.IO) {
            val searchSpec = SearchSpec.Builder()
                .addFilterNamespaces(SearchableAsignatura.NAMESPACE)
                .setRankingStrategy(SearchSpec.RANKING_STRATEGY_USAGE_COUNT)
                .build()

            val result = session?.search(query, searchSpec)
                ?: return@withContext emptyList()

            val page = result.nextPageAsync.get()
            page.mapNotNull {
                if (it.genericDocument.schemaType == SearchableAsignatura::class.java.simpleName) {
                    it.getDocument(SearchableAsignatura::class.java)
                }
                else null
            }
        }
    }

    override fun closeSession() {
        session?.close()
        session = null
    }
}