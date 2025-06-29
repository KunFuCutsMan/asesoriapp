package com.padieer.asesoriapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.style.TextAlign

private val DarkColorScheme = darkColorScheme(
    primary = Blue35,
    secondary = Cyan30,
    tertiary = Green45
)

private val LightColorScheme = lightColorScheme(
    primary = Blue45,
    secondary = Cyan40,
    tertiary = Green55

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun AsesoriAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography.copy(
            titleLarge = Typography.titleLarge.copy(
                color = colorScheme.primary,
            ),
            titleMedium = Typography.titleMedium.copy(
                color = colorScheme.primary,
            ),
            titleSmall = Typography.titleSmall.copy(
                color = colorScheme.primary,
            )
        ),
        content = content
    )
}