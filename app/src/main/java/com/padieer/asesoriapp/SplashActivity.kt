package com.padieer.asesoriapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.padieer.asesoriapp.ui.SplashScreen
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    private val splashDelay: Long = 3000 // 3 segundos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AsesoriAppTheme {
                SplashScreen()
            }
        }
        /*
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Carga las vistas
        val logoImage = findViewById<ImageView>(R.id.logoImage)
        val appName = findViewById<TextView>(R.id.appName)

        // Carga animaciones
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up)

        // Aplica animaciones
        logoImage.startAnimation(scaleUp)
        appName.startAnimation(fadeIn)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, splashDelay)
        */
    }
}