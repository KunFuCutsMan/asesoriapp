package com.padieer.asesoriapp.domain.error

sealed interface ValidationError: Error {

    enum class NombreError: ValidationError {
        NOT_EMPTY,
        TOO_LONG,
        NOT_ALPHA,
    }

    enum class ApellidoError: ValidationError {
        NOT_EMPTY,
        TOO_LONG,
        NOT_ALPHA,
    }

    enum class NumeroControlError: ValidationError {
        NOT_EMPTY,
        NOT_NUMERIC,
        WRONG_LENGTH,
    }

    enum class TelefonoError: ValidationError {
        NOT_EMPTY,
        NOT_NUMERIC,
        WRONG_LENGTH,
        NOT_VALID,
    }

    enum class SemestreError: ValidationError {
        NOT_VALID,
    }

    enum class CarreraError: ValidationError {
        NOT_EMPTY,
        NOT_ALPHA,
        NOT_FOUND,
    }

    enum class ContrasenaError: ValidationError {
        NOT_EMPTY,
        TOO_SHORT,
        TOO_LONG,
        NEEDS_LETTER,
        NEEDS_DIGIT,
        NOT_MIXED_CASE
    }

    enum class ContrasenaRepiteError: ValidationError {
        NOT_EMPTY,
        NOT_EQUAL,
    }
}

fun ValidationError.NombreError.message(): String = when (this) {
    ValidationError.NombreError.NOT_EMPTY -> "Nombre no debe de ser vacío"
    ValidationError.NombreError.TOO_LONG -> "Nombre no puede ser mayor a 32 caracteres"
    ValidationError.NombreError.NOT_ALPHA -> "Nombre solo puede ser contener letras"
}

fun ValidationError.ApellidoError.message(): String = when (this) {
    ValidationError.ApellidoError.NOT_EMPTY -> "Apellido no debe de ser vacío"
    ValidationError.ApellidoError.TOO_LONG -> "Apellido no puede ser mayor a 32 caracteres"
    ValidationError.ApellidoError.NOT_ALPHA -> "Apellido solo puede ser contener letras"
}

fun ValidationError.NumeroControlError.message(): String = when (this) {
    ValidationError.NumeroControlError.NOT_EMPTY -> "Numero de Control no debe de ser vacío"
    ValidationError.NumeroControlError.NOT_NUMERIC -> "Numero de Control debe de ser numérico"
    ValidationError.NumeroControlError.WRONG_LENGTH -> "Numero de Control debe de ser de 8 números"
}

fun ValidationError.TelefonoError.message(): String = when (this) {
    ValidationError.TelefonoError.NOT_EMPTY -> "Número Telefónico no debe de ser vacío"
    ValidationError.TelefonoError.NOT_NUMERIC -> "Número Telefónico debe ser numérico"
    ValidationError.TelefonoError.WRONG_LENGTH -> "Número Telefónico debe ser de 10 dígitos"
    ValidationError.TelefonoError.NOT_VALID -> "Número Telefónico no es válido"
}

fun ValidationError.SemestreError.message(): String = when (this) {
    ValidationError.SemestreError.NOT_VALID -> "Semestre no es válido"
}

fun ValidationError.CarreraError.message(): String = when (this) {
    ValidationError.CarreraError.NOT_EMPTY -> "Carrera no debe de ser vacío"
    ValidationError.CarreraError.NOT_ALPHA -> "Carrera debe contener letras"
    ValidationError.CarreraError.NOT_FOUND -> "Carrera no existe"
}

fun ValidationError.ContrasenaError.message(): String = when (this) {
    ValidationError.ContrasenaError.NOT_EMPTY -> "Contraseña no debe ser vacía"
    ValidationError.ContrasenaError.TOO_SHORT -> "Contraseña debe tener al menos 8 caracteres"
    ValidationError.ContrasenaError.TOO_LONG -> "Contraseña no debe exceder 32 caracteres"
    ValidationError.ContrasenaError.NEEDS_LETTER -> "Contraseña debe incluir un caracter"
    ValidationError.ContrasenaError.NEEDS_DIGIT -> "Contraseña debe incluir un dígito"
    ValidationError.ContrasenaError.NOT_MIXED_CASE -> "Contraseña debe incluir al menos un caracter mayúsculo y otro minúsculo"
}

fun ValidationError.ContrasenaRepiteError.message(): String = when (this) {
    ValidationError.ContrasenaRepiteError.NOT_EMPTY -> "Repite tu contraseña"
    ValidationError.ContrasenaRepiteError.NOT_EQUAL -> "La contraseña no es la misma"
}