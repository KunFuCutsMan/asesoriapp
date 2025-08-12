package com.padieer.asesoriapp.domain.search

/**
 * Interfaz aplicable para modelos que en algun lado de la aplicación se buscan, sea mediante por
 * [com.padieer.asesoriapp.ui.common.search.OutlinedSearchField] o alguna lógica empresarial.
 *
 * @property displayName Texto que representa el texto una vez representado en la UI
 */
interface Searchable {
    val displayName: String
}