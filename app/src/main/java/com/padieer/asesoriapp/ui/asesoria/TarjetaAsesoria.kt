package com.padieer.asesoriapp.ui.asesoria

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.padieer.asesoriapp.data.asesoria.FakeAsesoriaRepository
import com.padieer.asesoriapp.domain.error.Result
import com.padieer.asesoriapp.domain.model.Asesoria
import com.padieer.asesoriapp.domain.model.toUIModel
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme

@Composable
fun TarjetaAsesoria(modifier: Modifier = Modifier, asesoria: Asesoria) {

}

@Preview
@Composable
private fun TarjetaAsesoriaPreview() {
    var asesoria by remember { mutableStateOf<Asesoria?>(null) }

    LaunchedEffect(true) {
        asesoria = (FakeAsesoriaRepository().fetchAsesoriasOfEstudiante() as Result.Success)
            .data.first().toUIModel()
    }

    AsesoriAppTheme {
        if (asesoria != null)
            TarjetaAsesoria(
                asesoria = asesoria!!
            )
    }
}