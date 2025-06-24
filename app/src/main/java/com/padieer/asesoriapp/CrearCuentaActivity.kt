package com.padieer.asesoriapp

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.StrictMode
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class CrearCuentaActivity : AppCompatActivity() {

    private lateinit var edtNombres: EditText
    private lateinit var edtApellidoP: EditText
    private lateinit var edtApellidoM: EditText
    private lateinit var edtNumControl: EditText
    private lateinit var edtNumTel: EditText
    private lateinit var edtSemestre: EditText
    private lateinit var edtCarrera: EditText
    private lateinit var edtContrasena: EditText
    private lateinit var edtRepetirContrasena: EditText
    private lateinit var btnCrearCuenta: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cuenta)

        // Permitir operaciones de red en el hilo principal (sólo para pruebas, evita en producción)
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())

        edtNombres = findViewById(R.id.edtNombres)
        edtApellidoP = findViewById(R.id.edtApellidoP)
        edtApellidoM = findViewById(R.id.edtApellidoM)
        edtNumControl = findViewById(R.id.edtNumControl)
        edtNumTel = findViewById(R.id.edtNumTel)
        edtSemestre = findViewById(R.id.edtSemestre)
        edtCarrera = findViewById(R.id.edtCarrera)
        edtContrasena = findViewById(R.id.edtContrasena)
        edtRepetirContrasena = findViewById(R.id.edtRepetirContrasena)
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta)

        val btnIrInicioSesion: Button = findViewById(R.id.btnIrInicioSesion)
        btnIrInicioSesion.setOnClickListener {
            val intent = Intent(this, AsesoradoInicioSesionActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnCrearCuenta.setOnClickListener {
            if (validarCampos()) {
                enviarDatosAlServidor()
            } else {
                Toast.makeText(this, "Revisa los campos en rojo.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun enviarDatosAlServidor() {
        val json = JSONObject()
        json.put("nombre", edtNombres.text.toString())
        json.put("apellidoPaterno", edtApellidoP.text.toString())
        json.put("apellidoMaterno", edtApellidoM.text.toString())
        json.put("numeroControl", edtNumControl.text.toString())
        json.put("numeroTelefonico", edtNumTel.text.toString())
        json.put("semestre", edtSemestre.text.toString().toInt())
        json.put("carrera", edtCarrera.text.toString())
        json.put("contrasena", edtContrasena.text.toString()) // Se encripta en el servidor

        try {
            val url = URL("http://10.0.2.2:5000/registro_estudiante")
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.setRequestProperty("Content-Type", "application/json; utf-8")
            conn.doOutput = true

            val output = OutputStreamWriter(conn.outputStream)
            output.write(json.toString())
            output.flush()

            val responseCode = conn.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                runOnUiThread {
                    Toast.makeText(this, "Cuenta creada exitosamente!", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, AsesoradoInicioSesionActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            } else {
                runOnUiThread {
                    Toast.makeText(this, "Error al crear cuenta: $responseCode", Toast.LENGTH_LONG).show()
                }
            }
            conn.disconnect()
        } catch (e: Exception) {
            Log.e("CrearCuenta", "Error: ${e.message}")
            runOnUiThread {
                Toast.makeText(this, "Excepción: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }

        // Validación Número Control: máximo 8 dígitos numéricos
        edtNumControl.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                val valido = text.matches(Regex("^\\d{1,8}\$"))
                setEditTextColor(edtNumControl, valido)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Validación Teléfono: solo números, mínimo 7 y máximo 15 dígitos para evitar teléfonos inválidos
        edtNumTel.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                val valido = text.matches(Regex("^\\d{7,15}\$"))
                setEditTextColor(edtNumTel, valido)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Validación Semestre: solo números enteros del 1 al 12
        edtSemestre.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                val num = text.toIntOrNull()
                val valido = num != null && num in 1..12
                setEditTextColor(edtSemestre, valido)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Validación Contraseña: mínimo 6 caracteres
        edtContrasena.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val valido = s?.length ?: 0 >= 6
                setEditTextColor(edtContrasena, valido)
                // Además validar si coinciden las contraseñas al cambiar alguna
                validarCoincidenciaContrasenas()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Validación Repetir Contraseña: debe coincidir con contraseña
        edtRepetirContrasena.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validarCoincidenciaContrasenas()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        btnCrearCuenta.setOnClickListener {
            if (validarCampos()) {
                Toast.makeText(this, "Cuenta creada exitosamente!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Revisa los campos en rojo.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validarCoincidenciaContrasenas() {
        val pass = edtContrasena.text.toString()
        val repetir = edtRepetirContrasena.text.toString()
        val coincide = pass == repetir && pass.length >= 6
        setEditTextColor(edtRepetirContrasena, coincide)
    }

    private fun setEditTextColor(editText: EditText, isValid: Boolean) {
        val color = if (isValid) Color.GREEN else Color.RED
        editText.background.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }

    private fun validarCampos(): Boolean {
        val letrasRegex = Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+\$")

        val nombreValido = edtNombres.text.toString().matches(letrasRegex) && edtNombres.text.toString().isNotBlank()
        val apellidoPValido = edtApellidoP.text.toString().matches(letrasRegex) && edtApellidoP.text.toString().isNotBlank()
        val apellidoMValido = edtApellidoM.text.toString().matches(letrasRegex) && edtApellidoM.text.toString().isNotBlank()
        val carreraValida = edtCarrera.text.toString().matches(letrasRegex) && edtCarrera.text.toString().isNotBlank()

        val controlValido = edtNumControl.text.toString().matches(Regex("^\\d{1,8}\$"))
        val telValido = edtNumTel.text.toString().matches(Regex("^\\d{7,15}\$"))

        val semestreNum = edtSemestre.text.toString().toIntOrNull()
        val semestreValido = semestreNum != null && semestreNum in 1..12

        val pass = edtContrasena.text.toString()
        val repetir = edtRepetirContrasena.text.toString()
        val contrasenaValida = pass.length >= 6 && pass == repetir

        return nombreValido && apellidoPValido && apellidoMValido && controlValido && telValido &&
                semestreValido && carreraValida && contrasenaValida
    }
}
