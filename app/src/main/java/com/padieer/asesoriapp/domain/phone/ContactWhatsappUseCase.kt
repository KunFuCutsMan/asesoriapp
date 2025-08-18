package com.padieer.asesoriapp.domain.phone

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.net.toUri

class ContactWhatsappUseCase(private val context: Context?) {

    operator fun invoke(telefono: String) {
        try {
            val url = "https:api.whatsapp.com/send?phone=52$telefono"

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = url.toUri()
            intent.`package` = "com.whatsapp"

            if (context == null) return

            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            }
            else {
                Toast.makeText(context, "Instale Whatsapp", Toast.LENGTH_LONG).show()
            }
        }
        catch (e: Exception) {
            Toast.makeText(context, "Hubo un error al abrir Whatsapp: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}