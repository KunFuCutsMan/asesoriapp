package com.padieer.asesoriapp.domain.phone

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri

class ContactWhatsappUseCase(private val context: Context?) {

    operator fun invoke(telefono: String) {
        try {
            val url = "https://api.whatsapp.com/send?phone=+52$telefono".toUri()

            val intent = Intent(Intent.ACTION_VIEW, url)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.setPackage("com.whatsapp")

            context?.let {
                intent.resolveActivity(context.packageManager)?.let {
                    context.startActivity(intent)
                }
            }
        }
        catch (e: Exception) {
            Log.e("ContactWhatsappUseCase", e.toString())
            Toast.makeText(context, "Hubo un error al abrir Whatsapp: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}