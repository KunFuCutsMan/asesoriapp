package com.padieer.asesoriapp.data.carrera.sources

import com.padieer.asesoriapp.data.carrera.CarreraModel

class FakeCarreraSource {

     fun fetchCarreras(): Result<List<CarreraModel>> {
        return runCatching {
            listOf(
                CarreraModel(1,"Administración", "A" ),
                CarreraModel(2,"Bioquímica", "B" ),
                CarreraModel(3,"Eléctrica", "C" ),
                CarreraModel(4,"Electrónica", "D" ),
                CarreraModel(5,"Industrial", "E" ),
                CarreraModel(6,"Mecatrónica", "F" ),
                CarreraModel(7,"Mecánica", "G" ),
                CarreraModel(8,"Sistemas Computacionales", "H" ),
                CarreraModel(9,"Química", "I" ),
                CarreraModel(10,"Energías Renovables", "J" ),
                CarreraModel(11,"Gestión Empresarial", "K" ),
            )
        }
    }
}