package com.padieer.asesoriapp

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AdminInicioSesionActivity : AppCompatActivity() {

    private lateinit var edtClaveAdmin: EditText
    private lateinit var edtNumControlAdmin: EditText
    private lateinit var edtContrasenaAdmin: EditText
    private lateinit var btnIniciarSesionAdmin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_inicio_sesion)

        edtClaveAdmin = findViewById(R.id.edtClaveAdmin)
        edtNumControlAdmin = findViewById(R.id.edtNumControlAdmin)
        edtContrasenaAdmin = findViewById(R.id.edtContrasenaAdmin)
        btnIniciarSesionAdmin = findViewById(R.id.btnIniciarSesionAdmin)

        mostrarDialogoVerificacion()

        val btnVolver = findViewById<Button>(R.id.btnVolverAdmin)
        btnVolver.setOnClickListener {
            finish() // Vuelve a la actividad anterior
        }

        btnIniciarSesionAdmin.setOnClickListener {
            val clave = edtClaveAdmin.text.toString().trim()
            val numControl = edtNumControlAdmin.text.toString().trim()
            val contrasena = edtContrasenaAdmin.text.toString().trim()

            if (clave.isEmpty() || numControl.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            } else if (numControl.length > 8) {
                Toast.makeText(this, "Número de control no puede tener más de 8 dígitos", Toast.LENGTH_SHORT).show()
            } else {
                // Validación simulada
                Toast.makeText(this, "Inicio de sesión exitoso (Simulado)", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun mostrarDialogoVerificacion() {
        val view = layoutInflater.inflate(R.layout.dialog_admin_warning, null)
        val txtNoAdmin = view.findViewById<TextView>(R.id.btnNoAdmin)
        val txtSiAdmin = view.findViewById<TextView>(R.id.btnSiAdmin)

        val dialog = AlertDialog.Builder(this)
            .setView(view)
            .setCancelable(false)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()

        txtNoAdmin.setOnClickListener {
            dialog.dismiss()
            finish()
        }

        var counter = 5
        val handler = Handler(Looper.getMainLooper())
        val countdownRunnable = object : Runnable {
            override fun run() {
                if (counter > 1) {
                    counter--
                    txtSiAdmin.text = "Soy Admin (${counter}...)"
                    handler.postDelayed(this, 1000)
                } else {
                    txtSiAdmin.text = "Soy Admin"
                    txtSiAdmin.alpha = 1f
                    txtSiAdmin.isEnabled = true
                    txtSiAdmin.setOnClickListener {
                        dialog.dismiss()
                    }
                }
            }
        }

        handler.postDelayed(countdownRunnable, 1000)
    }
}
