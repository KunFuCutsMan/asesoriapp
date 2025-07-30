package com.padieer.asesoriapp.data.carrera.sources

import com.padieer.asesoriapp.domain.model.CarreraModel

class CacheCarreraSource() {

    var carreras: List<CarreraModel>? = null
        private set

    fun setCarreras(lista: List<CarreraModel>) {
        if (lista.isNotEmpty())
            this.carreras = lista
    }
}