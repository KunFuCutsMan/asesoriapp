package com.padieer.asesoriapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AsesorInicioSesionActivity : AppCompatActivity() {

    private lateinit var edtControl: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnAsesor: Button
    private lateinit var txtAdmin: TextView
    private lateinit var imgLogo: ImageView
    private lateinit var chkRecordar: CheckBox

    private val usuarioPrueba = "asesor123"
    private val contrasenaPrueba = "password123"

    private val prefsName = "prefs_asesor_login"
    private val keyRecordar = "recordar_usuario"
    private val keyUsuario = "usuario_guardado"
    private val keyPassword = "password_guardado"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asesor_inicio_sesion)

        edtControl = findViewById(R.id.edtControl)
        edtPassword = findViewById(R.id.edtPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnAsesor = findViewById(R.id.btnAsesor)
        txtAdmin = findViewById(R.id.txtAdmin)
        imgLogo = findViewById(R.id.imgLogo)
        chkRecordar = findViewById(R.id.chkRecordar)

        val prefs = getSharedPreferences(prefsName, Context.MODE_PRIVATE)

        // Cargar datos guardados si se eligió recordar usuario
        val recordar = prefs.getBoolean(keyRecordar, false)
        if (recordar) {
            val usuarioGuardado = prefs.getString(keyUsuario, "")
            val passwordGuardado = prefs.getString(keyPassword, "")
            edtControl.setText(usuarioGuardado)
            edtPassword.setText(passwordGuardado)
            chkRecordar.isChecked = true

            // Inicio automático
            if (usuarioGuardado == usuarioPrueba && passwordGuardado == contrasenaPrueba) {
                Toast.makeText(this, "Inicio de sesión automático", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, InicioAsesorActivity::class.java))
                finish()
            }
        }

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
                if (control == usuarioPrueba && password == contrasenaPrueba) {
                    // Guardar o eliminar preferencias según CheckBox
                    if (chkRecordar.isChecked) {
                        prefs.edit()
                            .putBoolean(keyRecordar, true)
                            .putString(keyUsuario, control)
                            .putString(keyPassword, password)
                            .apply()
                    } else {
                        prefs.edit().clear().apply()
                    }
                    Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, InicioAsesorActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnAsesor.setOnClickListener {
            startActivity(Intent(this, AsesoradoInicioSesionActivity::class.java))
        }

        txtAdmin.setOnClickListener {
            startActivity(Intent(this, AdminInicioSesionActivity::class.java))
        }
    }
}
