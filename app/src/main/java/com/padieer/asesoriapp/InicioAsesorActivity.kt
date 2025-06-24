package com.padieer.asesoriapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class InicioAsesorActivity : AppCompatActivity() {

    private lateinit var layoutAsesorias: LinearLayout
    private lateinit var layoutConfiguracion: LinearLayout
    private lateinit var btnGuardar: Button
    private lateinit var btnCerrarSesion: Button
    private lateinit var bottomNavAsesor: BottomNavigationView

    private lateinit var checkEcuaciones: CheckBox
    private lateinit var checkMetodos: CheckBox
    private lateinit var checkProbabilidad: CheckBox

    private val prefsName = "prefs_asesor_login"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_asesor)

        layoutAsesorias = findViewById(R.id.layoutAsesorias)
        layoutConfiguracion = findViewById(R.id.layoutConfiguracion)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)
        bottomNavAsesor = findViewById(R.id.bottomNavAsesor)

        checkEcuaciones = findViewById(R.id.checkEcuaciones)
        checkMetodos = findViewById(R.id.checkMetodos)
        checkProbabilidad = findViewById(R.id.checkProbabilidad)

        bottomNavAsesor.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_asesorias -> {
                    layoutAsesorias.visibility = View.VISIBLE
                    layoutConfiguracion.visibility = View.GONE
                }
                R.id.nav_configuracion -> {
                    layoutAsesorias.visibility = View.GONE
                    layoutConfiguracion.visibility = View.VISIBLE
                }
            }
            true
        }

        btnGuardar.setOnClickListener {
            val carrera = findViewById<EditText>(R.id.edtCarrera).text.toString()
            val horario = findViewById<EditText>(R.id.edtHorarioLibre).text.toString()
            val descripcion = findViewById<EditText>(R.id.edtDescripcion).text.toString()

            val materias = mutableListOf<String>()
            if (checkEcuaciones.isChecked) materias.add("Ecuaciones Diferenciales")
            if (checkMetodos.isChecked) materias.add("Métodos Numéricos")
            if (checkProbabilidad.isChecked) materias.add("Probabilidad y Estadística")

            Toast.makeText(
                this,
                "Configuración guardada:\nCarrera: $carrera\nHorario: $horario\nMaterias: ${materias.joinToString()}",
                Toast.LENGTH_LONG
            ).show()
        }

        btnCerrarSesion.setOnClickListener {
            mostrarDialogoCerrarSesion()
        }
    }

    private fun mostrarDialogoCerrarSesion() {
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.dialog_cerrar_sesion, null)
        builder.setView(dialogView)

        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)

        val btnConfirmar = dialogView.findViewById<Button>(R.id.btnConfirmar)
        val btnCancelar = dialogView.findViewById<Button>(R.id.btnCancelar)

        btnConfirmar.setOnClickListener {
            dialog.dismiss()
            cerrarSesion()
        }
        btnCancelar.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun cerrarSesion() {
        // Limpiar SharedPreferences para que no recuerde el usuario
        val prefs = getSharedPreferences(prefsName, MODE_PRIVATE)
        prefs.edit().clear().apply()

        // Regresar al login y limpiar backstack
        val intent = Intent(this, AsesorInicioSesionActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mostrarDialogoCerrarSesion()
    }
}
