package com.padieer.asesoriapp.di

import com.padieer.asesoriapp.data.asesoria.AsesoriaRepository
import com.padieer.asesoriapp.data.asignatura.AsignaturaRepository
import com.padieer.asesoriapp.data.carrera.CarreraRepository
import com.padieer.asesoriapp.data.estudiante.EstudianteRepository
import com.padieer.asesoriapp.data.horario.HorarioRepository
import com.padieer.asesoriapp.data.password.PasswordRepository
import com.padieer.asesoriapp.data.token.LoginRepository
import com.padieer.asesoriapp.domain.model.SearchableAsignatura
import com.padieer.asesoriapp.domain.phone.CallPhoneUseCase
import com.padieer.asesoriapp.domain.phone.ContactWhatsappUseCase
import com.padieer.asesoriapp.domain.search.Searcher

interface AppModule {
    val callPhoneUseCase: CallPhoneUseCase
    val contactWhatsappUseCase: ContactWhatsappUseCase
    val carreraRepository: CarreraRepository
    val asignaturaRepository: AsignaturaRepository
    val estudianteRepository: EstudianteRepository
    val loginRepository: LoginRepository
    val passwordRepository: PasswordRepository
    val horarioRepository: HorarioRepository

    val asesoriaRepository: AsesoriaRepository

    val asignaturaSearcher: Searcher<SearchableAsignatura>
}