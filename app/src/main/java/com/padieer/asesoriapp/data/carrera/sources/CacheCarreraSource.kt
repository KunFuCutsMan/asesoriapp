package com.padieer.asesoriapp.data.carrera.sources

import com.padieer.asesoriapp.data.carrera.CarreraModel

class CacheCarreraSource() {

    var carreras: List<CarreraModel>? = null
        private set

    fun setCarreras(lista: List<CarreraModel>) {
        if (lista.isNotEmpty())
            this.carreras = lista
    }
}