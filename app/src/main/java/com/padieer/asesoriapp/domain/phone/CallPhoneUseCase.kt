package com.padieer.asesoriapp.domain.phone

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.telephony.PhoneNumberUtils
import androidx.core.net.toUri

class CallPhoneUseCase(private val context: Context?) {

    operator fun invoke(telefono: String) {
        val numeroTelefono = PhoneNumberUtils.normalizeNumber(telefono)
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = numeroTelefono.toUri()

        if (context?.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            intent.resolveActivity(context.packageManager)?.let {
                context.startActivity(intent)
            }
        }
    }
}