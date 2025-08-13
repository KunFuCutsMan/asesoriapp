package com.padieer.asesoriapp.domain.model

import androidx.appsearch.annotation.Document
import androidx.appsearch.app.AppSearchSchema
import com.padieer.asesoriapp.domain.search.Searchable
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class AsignaturaModel(
    val id: Int,
    val nombre: String,
    val carreras: List<CarreraModel>
)

data class Asignatura(
    val nombre: String,
    val id: Int,
    override val key: String,
): Searchable {
    override val displayName = this.nombre
}

@Document
data class SearchableAsignatura(
    @Document.Namespace
    val namespace: String,

    @Document.Id
    val id: String,

    @Document.LongProperty
    val modelID: Int,

    @Document.StringProperty(
        indexingType = AppSearchSchema.StringPropertyConfig.INDEXING_TYPE_PREFIXES
    )
    val nombre: String,
) {
    companion object {
        const val NAMESPACE = "asesorias"
    }
}

fun AsignaturaModel.toUIModel() = Asignatura(
    nombre = this.nombre,
    id = this.id,
    key = this.nombre + this.id,
)

fun AsignaturaModel.toSearchable() = SearchableAsignatura(
    namespace = SearchableAsignatura.NAMESPACE,
    modelID = this.id,
    nombre = this.nombre,
    id = UUID.randomUUID().toString(),
)

fun SearchableAsignatura.toUIModel() = Asignatura(
    nombre = this.nombre,
    id = this.modelID,
    key = this.nombre + this.modelID
)