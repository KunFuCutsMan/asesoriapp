package com.padieer.asesoriapp.domain.error

sealed interface DataError: Error {
    enum class Network: DataError {
        TIMEOUT,
        NO_CONNECTION,
        BAD_PARAMS,
        UNAUTHENTICATED,
        NOT_FOUND,
        FORBIDDEN,
        UNKWOWN
    }

    enum class Local: DataError {
        NOT_FOUND,
        DISK_FULL,
        UNKWOWN
    }
}