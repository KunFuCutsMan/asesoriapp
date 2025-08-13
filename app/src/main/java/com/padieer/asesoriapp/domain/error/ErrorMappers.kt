package com.padieer.asesoriapp.domain.error

fun <T> mapDataNetworkError(statusCode: Int): Result<T, DataError.Network> {
    return when (statusCode) {
        302, 400, 422 -> Result.Error(DataError.Network.BAD_PARAMS)
        401 -> Result.Error(DataError.Network.UNAUTHENTICATED)
        403 -> Result.Error(DataError.Network.FORBIDDEN)
        404 -> Result.Error(DataError.Network.NOT_FOUND)
        else -> Result.Error(DataError.Network.UNKWOWN)
    }
}