package com.padieer.asesoriapp.di

import com.padieer.asesoriapp.data.asesoria.AsesoriaRepository
import com.padieer.asesoriapp.data.asesoria.FakeAsesoriaRepository
import com.padieer.asesoriapp.data.asignatura.AsignaturaRepository
import com.padieer.asesoriapp.data.asignatura.FakeAsignaturaRepository
import com.padieer.asesoriapp.data.asignatura.FakeAsignaturaSearcher
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
import com.padieer.asesoriapp.domain.model.SearchableAsignatura
import com.padieer.asesoriapp.domain.phone.CallPhoneUseCase
import com.padieer.asesoriapp.domain.search.Searcher

class FakeAppModule() : AppModule {
    override val carreraRepository: CarreraRepository = FakeCarreraRepository()
    override val estudianteRepository: EstudianteRepository = FakeEstudianteRepository()
    override val loginRepository: LoginRepository = FakeLoginRepository()
    override val passwordRepository: PasswordRepository = FakePasswordRepository()
    override val horarioRepository: HorarioRepository = FakeHorarioRepository()
    override val asignaturaRepository: AsignaturaRepository = FakeAsignaturaRepository()
    override val callPhoneUseCase: CallPhoneUseCase = CallPhoneUseCase(null)
    override val asesoriaRepository: AsesoriaRepository = FakeAsesoriaRepository()
    override val asignaturaSearcher: Searcher<SearchableAsignatura> = FakeAsignaturaSearcher(this.asignaturaRepository)
}