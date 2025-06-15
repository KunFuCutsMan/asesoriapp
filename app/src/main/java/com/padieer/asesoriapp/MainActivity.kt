package com.padieer.asesoriapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias a las vistas
        val edtControl = findViewById<EditText>(R.id.edtControl)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnAsesor = findViewById<Button>(R.id.btnAsesor)
        val txtCrearCuenta = findViewById<TextView>(R.id.txtCrearCuenta)
        val txtOlvideContrasena = findViewById<TextView>(R.id.txtOlvideContrasena)

        // Botón Iniciar Sesión
        btnLogin.setOnClickListener {
            val control = edtControl.text.toString()
            val password = edtPassword.text.toString()

            if (control.isNotEmpty() && password.isNotEmpty()) {
                val intent = Intent(this, InicioActivity::class.java)
                startActivity(intent)
            }
        }

        // Texto Crear Cuenta
        txtCrearCuenta.setOnClickListener {
            val intent = Intent(this, CrearCuentaActivity::class.java)
            startActivity(intent)
        }

        // Texto Olvidé Contraseña
        txtOlvideContrasena.setOnClickListener {
            val intent = Intent(this, RecupContraActivity::class.java)
            startActivity(intent)
        }

        // Botón Asesor
        btnAsesor.setOnClickListener {
            val intent = Intent(this, AsesorInicioSesionActivity::class.java)
            startActivity(intent)
        }
    }
}
