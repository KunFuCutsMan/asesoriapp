package com.padieer.asesoriapp.di

import com.padieer.asesoriapp.data.carrera.CarreraRepository
import com.padieer.asesoriapp.data.estudiante.EstudianteRepository
import com.padieer.asesoriapp.data.password.PasswordRepository
import com.padieer.asesoriapp.data.token.LoginRepository

interface AppModule {
    val carreraRepository: CarreraRepository
    val estudianteRepository: EstudianteRepository
    val loginRepository: LoginRepository
    val passwordRepository: PasswordRepository
}