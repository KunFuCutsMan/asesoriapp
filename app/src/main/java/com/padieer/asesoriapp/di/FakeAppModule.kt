package com.padieer.asesoriapp.di

import com.padieer.asesoriapp.data.carrera.CarreraRepository
import com.padieer.asesoriapp.data.carrera.FakeCarreraRepository
import com.padieer.asesoriapp.data.estudiante.EstudianteRepository
import com.padieer.asesoriapp.data.estudiante.FakeEstudianteRepository
import com.padieer.asesoriapp.data.token.FakeLoginRepository
import com.padieer.asesoriapp.data.token.LoginRepository

class FakeAppModule(
    override val carreraRepository: CarreraRepository = FakeCarreraRepository(),
    override val estudianteRepository: EstudianteRepository = FakeEstudianteRepository(),
    override val loginRepository: LoginRepository = FakeLoginRepository()
) : AppModule