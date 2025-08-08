package com.padieer.asesoriapp.di

import com.padieer.asesoriapp.data.asignatura.AsignaturaRepository
import com.padieer.asesoriapp.data.asignatura.FakeAsignaturaRepository
import com.padieer.asesoriapp.data.carrera.CarreraRepository
import com.padieer.asesoriapp.data.carrera.FakeCarreraRepository
import com.padieer.asesoriapp.data.estudiante.EstudianteRepository
import com.padieer.asesoriapp.data.estudiante.FakeEstudianteRepository
import com.padieer.asesoriapp.data.horario.FakeHorarioRepository
import com.padieer.asesoriapp.data.horario.HorarioRepository
import com.padieer.asesoriapp.data.password.FakePasswordRepository
import com.padieer.asesoriapp.data.password.PasswordRepository
import com.padieer.asesoriapp.data.token.FakeLoginRepository
import com.padieer.asesoriapp.data.token.LoginRepository

class FakeAppModule(
    override val carreraRepository: CarreraRepository = FakeCarreraRepository(),
    override val estudianteRepository: EstudianteRepository = FakeEstudianteRepository(),
    override val loginRepository: LoginRepository = FakeLoginRepository(),
    override val passwordRepository: PasswordRepository = FakePasswordRepository(),
    override val horarioRepository: HorarioRepository = FakeHorarioRepository(),
    override val asignaturaRepository: AsignaturaRepository = FakeAsignaturaRepository()
) : AppModule