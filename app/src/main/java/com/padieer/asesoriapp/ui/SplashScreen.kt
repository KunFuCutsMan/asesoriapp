package com.padieer.asesoriapp.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.padieer.asesoriapp.R
import com.padieer.asesoriapp.ui.nav.Screen
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen( navController: NavController? ) {

    LaunchedEffect(key1 = true) {
        delay(3000L)
        navController?.navigate( Screen.InicioSesionScreen.route )
    }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background( color = MaterialTheme.colorScheme.secondary )
            .padding(24.dp)
    ) {
        Image(
            painter = painterResource( id = R.drawable.logo ),
            contentDescription = "Logo",
            modifier = Modifier.size(250.dp)
        )
        Text("Aplicación Oficial",
            style = TextStyle(
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = TextUnit(26f, type = TextUnitType.Sp),
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(
                    top = 20.dp
                )
        )

    }
}

@Preview
@Composable
fun DefaultPreview() {
    AsesoriAppTheme {
        SplashScreen( navController = null )
    }
}