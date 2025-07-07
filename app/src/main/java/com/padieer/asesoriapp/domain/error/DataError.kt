package com.padieer.asesoriapp.domain.error

sealed interface DataError: Error {
    enum class Network: DataError {
        BAD_PARAMS,
        UNKWOWN
    }

    enum class Local: DataError {
        NOT_FOUND,
        DISK_FULL,
        UNKWOWN
    }
}