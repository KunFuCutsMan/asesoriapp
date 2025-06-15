package com.padieer.asesoriapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import android.widget.Toast

class AsesoradoInicioSesionActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asesorado_inicio_sesion)

        // Referencias a las vistas
        val edtControl = findViewById<EditText>(R.id.edtControl)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnAsesor = findViewById<Button>(R.id.btnAsesor)
        val txtCrearCuenta = findViewById<TextView>(R.id.txtCrearCuenta)
        val txtOlvideContrasena = findViewById<TextView>(R.id.txtOlvideContrasena)

        // Botón Iniciar Sesión
        btnLogin.setOnClickListener {
            val control = edtControl.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (control.isEmpty()) {
                edtControl.error = "Ingrese su número de control"
                edtControl.requestFocus()
            } else if (password.isEmpty()) {
                edtPassword.error = "Ingrese su contraseña"
                edtPassword.requestFocus()
            } else {
                // Aquí podrías validar con base de datos o API
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, InicioAsesoradoActivity::class.java)
                startActivity(intent)
            }
        }

        // Crear Cuenta
        txtCrearCuenta.setOnClickListener {
            startActivity(Intent(this, CrearCuentaActivity::class.java))
        }

        // Recuperar Contraseña
        txtOlvideContrasena.setOnClickListener {
            startActivity(Intent(this, RecupContraActivity::class.java))
        }

        // Cambio a login de Asesor
        btnAsesor.setOnClickListener {
            startActivity(Intent(this, AsesorInicioSesionActivity::class.java))
        }
    }
}
