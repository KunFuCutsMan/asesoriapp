package com.padieer.asesoriapp.ui.asesoria.asignarAsesor

sealed class AsignaAsesorUIState {

    data object Loading: AsignaAsesorUIState()
    data class Error(val error: String): AsignaAsesorUIState()

    /**
     * Modifica este estado a lo que consideres justo y necesario
     *
     * Recuerda utilizar los modelos que se encuentran en `domain/model`
     * (aquellos que no tienen el sufijo `Model`)
     */
    data class AsignaAsesor(
        val algo: String
    ): AsignaAsesorUIState()
}