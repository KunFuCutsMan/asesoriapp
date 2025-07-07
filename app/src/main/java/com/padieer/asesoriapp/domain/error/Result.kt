package com.padieer.asesoriapp.domain.error

typealias RootError = Error

/**
 * Interfaz que encapsula todos los errores posibles dentro de la app.
 *
 * @author <a href="https://github.com/philipplackner/CleanErrorHandling/">Philip Plackner</a>
 */
interface Result<out D, out E: RootError> {
    data class Success<out D, out E: RootError>(val data: D): Result<D, E>
    data class Error<out D, out E: RootError>(val error: E): Result<D, E>
}