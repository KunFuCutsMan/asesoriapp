package com.padieer.asesoriapp.domain.phone

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.telephony.PhoneNumberUtils
import android.widget.Toast
import androidx.core.net.toUri

class CallPhoneUseCase(private val context: Context?) {

    operator fun invoke(telefono: String) {
        val numeroTelefono = PhoneNumberUtils.normalizeNumber(telefono)
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = numeroTelefono.toUri()

        if (context?.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Llamando a $telefono", Toast.LENGTH_SHORT).show()
            intent.resolveActivity(context.packageManager)?.let {
                context.startActivity(intent)
            }
        }
        else {
            Toast.makeText(context, "No hay permiso para llamar el telefono", Toast.LENGTH_LONG).show()
        }
    }
}