package com.padieer.asesoriapp.data.token

import com.padieer.asesoriapp.crypto.LocalPreferencesSource
import com.padieer.asesoriapp.data.token.sources.RemoteTokenSource
import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result

class LoginRepositoryImpl(
    private val remoteTokenSource: RemoteTokenSource,
    private val localTokenSource: LocalPreferencesSource,
): LoginRepository {
    override suspend fun getToken(numControl: String, contrasena: String): Result<String, DataError.Network> {
        // Check if there's one locally
        val localResult = localTokenSource.fetchToken()
        if (localResult is Result.Success)
            return Result.Success(localResult.data)

        // Get the remote version
        val remoteResult = remoteTokenSource.fetchToken(numControl, contrasena)
        if ( remoteResult is Result.Success )
            localTokenSource.saveToken(remoteResult.data)

        return remoteResult
    }
}