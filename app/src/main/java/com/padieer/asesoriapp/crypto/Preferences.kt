package com.padieer.asesoriapp.crypto

import android.util.Base64
import androidx.datastore.core.Serializer
import com.padieer.asesoriapp.domain.model.EstudianteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Serializable
data class Preferences(
    val token: String? = null,
    val estudiante: EstudianteModel? = null
)

/**
 * Objeto que se encarga de serializar las preferencias del usuario
 *
 * @author Phillip Lackner
 * @see <a href="https://github.com/philipplackner/EncryptedDataStore">EncryptedDataStore</a>
 */
object PreferencesSerializer: Serializer<Preferences> {
    override val defaultValue: Preferences
        get() = Preferences()

    override suspend fun readFrom(input: InputStream): Preferences {
        val encryptedBytes = withContext(Dispatchers.IO) {
            input.use { it.readBytes() }
        }
        val encryptedBytesDecoded = Base64.decode(encryptedBytes, Base64.DEFAULT)
        val decryptedBytes = Crypto.decrypt(encryptedBytesDecoded)
        val decodedJsonString = decryptedBytes.decodeToString()
        return Json.decodeFromString(decodedJsonString)
    }

    override suspend fun writeTo(t: Preferences, output: OutputStream) {
        val json = Json.encodeToString(t)
        val bytes = json.toByteArray()
        val encryptedBytes = Crypto.encrypt(bytes)
        val encryptedBytesBase64 = Base64.encode(encryptedBytes, Base64.DEFAULT)
        withContext(Dispatchers.IO) {
            output.use {
                it.write(encryptedBytesBase64)
            }
        }
    }
}