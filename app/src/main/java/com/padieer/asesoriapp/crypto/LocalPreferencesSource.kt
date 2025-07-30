package com.padieer.asesoriapp.crypto

import android.content.Context
import androidx.datastore.core.IOException
import androidx.datastore.dataStore
import com.padieer.asesoriapp.domain.model.EstudianteModel
import com.padieer.asesoriapp.domain.error.DataError
import com.padieer.asesoriapp.domain.error.Result
import kotlinx.coroutines.flow.first

private val Context.dataStore by dataStore(
    fileName = "preferences",
    serializer = PreferencesSerializer
)

class LocalPreferencesSource(
    private val context: Context
) {
    suspend fun deletePreferences(): Result<Unit, DataError.Local> {
        try {
            context.dataStore.updateData { Preferences() }
            return Result.Success(Unit)
        }
        catch (e: Exception) {
            return when (e) {
                is IOException -> Result.Error(DataError.Local.DISK_FULL)
                else -> Result.Error(DataError.Local.UNKWOWN)
            }
        }
    }

    suspend fun fetchToken(): Result<String, DataError.Local> {
        val token = context.dataStore.data.first().token
        return if (token != null) Result.Success(token) else Result.Error(DataError.Local.NOT_FOUND)
    }

    suspend fun saveToken(token: String): Result<Unit, DataError.Local> {
        try {
            context.dataStore.updateData { it.copy(token = token) }
            return Result.Success(Unit)
        }
        catch (e: Exception) {
            return when(e) {
                is IOException -> Result.Error(DataError.Local.DISK_FULL)
                else -> Result.Error(DataError.Local.UNKWOWN)
            }
        }
    }

    suspend fun fetchCurrentEstudiante(): Result<EstudianteModel, DataError.Local> {
        val estudiante = context.dataStore.data.first().estudiante
        return if (estudiante != null) Result.Success(estudiante) else Result.Error(DataError.Local.NOT_FOUND)
    }

    suspend fun saveEstudiante(estudianteModel: EstudianteModel): Result<Unit, DataError.Local> {
        try {
            context.dataStore.updateData { it.copy(estudiante = estudianteModel) }
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