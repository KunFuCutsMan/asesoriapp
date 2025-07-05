package com.padieer.asesoriapp.data.token.sources

import android.content.Context
import androidx.datastore.dataStore
import com.padieer.asesoriapp.crypto.Preferences
import com.padieer.asesoriapp.crypto.PreferencesSerializer
import kotlinx.coroutines.flow.first

private val Context.dataStore by dataStore(
    fileName = "preferences",
    serializer = PreferencesSerializer
)

class LocalTokenSource(
    private val context: Context
) {

    suspend fun fetchToken(): String? {
        return context.dataStore.data.first().token
    }

    suspend fun saveToken(token: String) {
        context.dataStore.updateData {
            Preferences(
                token = token
            )
        }
    }
}