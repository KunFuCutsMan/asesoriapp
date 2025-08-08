package com.padieer.asesoriapp.data.carrera.sources

import com.padieer.asesoriapp.domain.model.CarreraModel

class FakeCarreraSource {

     fun fetchCarreras(): Result<List<CarreraModel>> {
        return runCatching { carreras() }
    }

    companion object {
        fun carreras() = listOf(
            CarreraModel(1,"Administración", "A", null ),
            CarreraModel(2,"Bioquímica", "B", null ),
            CarreraModel(3,"Eléctrica", "C", null ),
            CarreraModel(4,"Electrónica", "D", null ),
            CarreraModel(5,"Industrial", "E", null ),
            CarreraModel(6,"Mecatrónica", "F", null ),
            CarreraModel(7,"Mecánica", "G", null ),
            CarreraModel(8,"Sistemas Computacionales", "H", null ),
            CarreraModel(9,"Química", "I", null ),
            CarreraModel(10,"Energías Renovables", "J", null ),
            CarreraModel(11,"Gestión Empresarial", "K", null ),
        )
    }
}