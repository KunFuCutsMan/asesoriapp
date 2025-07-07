package com.padieer.asesoriapp.data.token.sources

import android.content.Context
import androidx.datastore.core.IOException
import androidx.datastore.dataStore
import com.padieer.asesoriapp.crypto.Preferences
import com.padieer.asesoriapp.crypto.PreferencesSerializer
import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import kotlinx.coroutines.flow.first

private val Context.dataStore by dataStore(
    fileName = "preferences",
    serializer = PreferencesSerializer
)

class LocalTokenSource(
    private val context: Context
) {

    suspend fun fetchToken(): Result<String, DataError.Local> {
        val token = context.dataStore.data.first().token
        return if (token != null) Result.Success(token) else Result.Error(DataError.Local.NOT_FOUND)
    }

    suspend fun saveToken(token: String): Result<Unit, DataError.Local> {
        try {
            context.dataStore.updateData { Preferences(token = token) }
            return Result.Success(Unit)
        }
        catch (e: Exception) {
            return when(e) {
                is IOException -> Result.Error(DataError.Local.DISK_FULL)
                else -> Result.Error(DataError.Local.UNKWOWN)
            }
        }
    }
}