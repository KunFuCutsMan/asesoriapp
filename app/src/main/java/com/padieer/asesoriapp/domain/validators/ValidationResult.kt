package com.padieer.asesoriapp.domain.validators

data class ValidationResult (
    val isSuccessful: Boolean,
    val errorMessage: String? = null
)

fun String.isAlpha(): Boolean {
    return this.all { it.isLetter() || it.isWhitespace() }
}

fun String.isNumeric(): Boolean {
    return this.all { it.isDigit() }
}

fun String.hasMixedCase(): Boolean {
    return this.any { it.isLowerCase() } && this.any { it.isUpperCase() }
}

fun String.hasSymbols(): Boolean {
    return "/\\p{Z}|\\p{S}|\\p{P}/u".toRegex().containsMatchIn(this)
}