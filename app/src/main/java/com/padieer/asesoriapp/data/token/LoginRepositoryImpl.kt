package com.padieer.asesoriapp.data.token

import com.padieer.asesoriapp.data.token.sources.LocalTokenSource
import com.padieer.asesoriapp.data.token.sources.RemoteTokenSource
import io.ktor.http.isSuccess

class LoginRepositoryImpl(
    private val remoteTokenSource: RemoteTokenSource,
    private val localTokenSource: LocalTokenSource,
): LoginRepository {
    override suspend fun getToken(numControl: String, contrasena: String): String? {
        // Check if there's one locally
        val token = localTokenSource.fetchToken()
        if (token != null) return token

        // Get the remote version
        val result = remoteTokenSource.fetchToken(numControl, contrasena)
        result.fold(
            onSuccess = {
                if (!it.statusCode.isSuccess())
                    return null
                // Save the token
                val remoteToken = it.body
                localTokenSource.saveToken(remoteToken!!)
                return remoteToken
            },
            onFailure = {
                return null
            },
        )
    }
}