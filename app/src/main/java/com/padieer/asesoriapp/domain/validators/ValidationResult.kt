package com.padieer.asesoriapp.domain.validators

import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.error.ValidationError

typealias ValidationResult = Result<Unit, ValidationError>

fun ValidationResult.messageOrNull(): String? {
    return when (this) {
        is Result.Success -> null
        is Result.Error -> this.error.message
        else -> null
    }
}

fun String.isAlpha(): Boolean {
    return this.all { it.isLetter() || it.isWhitespace() }
}

fun String.isNumeric(): Boolean {
    return this.all { it.isDigit() }
}

fun String.hasMixedCase(): Boolean {
    return this.any { it.isLowerCase() } && this.any { it.isUpperCase() }
}