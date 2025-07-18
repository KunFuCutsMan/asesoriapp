package com.padieer.asesoriapp.domain.error

sealed interface ValidationError: Error {

    val message: String

    enum class NombreError: ValidationError {
        NOT_EMPTY { override val message = "Nombre no debe de ser vacío" },
        TOO_LONG { override val message = "Nombre no puede ser mayor a 32 caracteres" },
        NOT_ALPHA { override val message = "Nombre solo puede ser contener letras" }
    }

    enum class ApellidoError: ValidationError {
        NOT_EMPTY { override val message = "Apellido no debe de ser vacío" },
        TOO_LONG { override val message = "Apellido no puede ser mayor a 32 caracteres" },
        NOT_ALPHA { override val message = "Apellido solo puede ser contener letras" },
    }

    enum class NumeroControlError: ValidationError {
        NOT_EMPTY { override val message = "Numero de Control no debe de ser vacío" },
        NOT_NUMERIC { override val message = "Numero de Control debe de ser numérico" },
        WRONG_LENGTH { override val message = "Numero de Control debe de ser de 8 números" },
    }

    enum class TelefonoError: ValidationError {
        NOT_EMPTY { override val message = "Número Telefónico no debe de ser vacío" },
        NOT_NUMERIC { override val message = "Número Telefónico debe ser numérico" },
        WRONG_LENGTH { override val message = "Número Telefónico debe ser de 10 dígitos" },
        NOT_VALID { override val message = "Número Telefónico no es válido" },
    }

    enum class SemestreError: ValidationError {
        NOT_VALID { override val message = "Semestre no es válido" },
    }

    enum class CarreraError: ValidationError {
        NOT_EMPTY { override val message = "Carrera no debe de ser vacío" },
        NOT_ALPHA { override val message = "Carrera debe contener letras" },
        NOT_FOUND { override val message = "Carrera no existe" },
    }

    enum class ContrasenaError: ValidationError {
        NOT_EMPTY { override val message = "Contraseña no debe ser vacía" },
        TOO_SHORT { override val message = "Contraseña debe tener al menos 8 caracteres" },
        TOO_LONG { override val message = "Contraseña no debe exceder 32 caracteres" },
        NEEDS_LETTER { override val message = "Contraseña debe incluir un caracter" },
        NEEDS_DIGIT { override val message = "Contraseña debe incluir un dígito" },
        NOT_MIXED_CASE { override val message = "Contraseña debe incluir al menos un caracter mayúsculo y otro minúsculo" }
    }

    enum class ContrasenaRepiteError: ValidationError {
        NOT_EMPTY { override val message = "Repite tu contraseña" },
        NOT_EQUAL { override val message = "La contraseña no es la misma" },
    }
}