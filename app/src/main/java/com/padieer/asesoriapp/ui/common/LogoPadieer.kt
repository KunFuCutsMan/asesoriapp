package com.padieer.asesoriapp.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.padieer.asesoriapp.R

@Composable
fun LogoPadieer(modifier: Modifier = Modifier) {
    if (isSystemInDarkTheme()) {
        Image(
            painter = painterResource(id = R.drawable.logoblanco),
            contentDescription = "Logo PADIEER",
            modifier = modifier
        )
    }
    else {
        Image(
            painter = painterResource(id = R.drawable.logonegro),
            contentDescription = "Logo PADIEER",
            modifier = modifier
        )
    }
}

@Composable
fun LogoPadieerSinLetras(modifier: Modifier = Modifier) {
    if (isSystemInDarkTheme()) {
        Image(
            painter = painterResource(id = R.drawable.logosinletras_blanco),
            contentDescription = "Logo PADIEER",
            modifier = modifier
        )
    }
    else {
        Image(
            painter = painterResource(id = R.drawable.logosinletras_negro),
            contentDescription = "Logo PADIEER",
            modifier = modifier
        )
    }
}