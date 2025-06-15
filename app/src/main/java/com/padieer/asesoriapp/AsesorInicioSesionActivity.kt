package com.padieer.asesoriapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AsesorInicioSesionActivity : AppCompatActivity() {

    // Declaración de variables
    private lateinit var edtControl: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnAsesor: Button
    private lateinit var txtAdmin: TextView
    private lateinit var imgLogo: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asesor_inicio_sesion)

        // Inicialización de vistas
        edtControl = findViewById(R.id.edtControl)
        edtPassword = findViewById(R.id.edtPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnAsesor = findViewById(R.id.btnAsesor)
        txtAdmin = findViewById(R.id.txtAdmin)
        imgLogo = findViewById(R.id.imgLogo)

        // Acción para el botón de login
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
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, InicioAsesorActivity::class.java)
                startActivity(intent)
            }
        }

        // Cambio a login de Asesorado
        btnAsesor.setOnClickListener {
            startActivity(Intent(this, AsesoradoInicioSesionActivity::class.java))
        }

        // Acceso a login de Admin
        txtAdmin.setOnClickListener {
            startActivity(Intent(this, AdminInicioSesionActivity::class.java))
        }
    }
}
