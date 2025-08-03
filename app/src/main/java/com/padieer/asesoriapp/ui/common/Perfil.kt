package com.padieer.asesoriapp.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.padieer.asesoriapp.domain.model.CarreraModel
import com.padieer.asesoriapp.domain.model.EstudianteModel
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme

data class Estudiante(
    val id: Int,
    val nombre: String,
    val apePaterno: String,
    val apeMaterno: String,
    val numeroTelefono: String,
    val numeroControl: String,
    val semestre: Int,
    val asesor: Asesor?,
    val admin: Admin?,
)

data class Carrera(
    val id: Int,
    val nombre: String,
)

data class Asesor(
    val id: Int,
)

data class Admin(
    val id: Int
)

fun CarreraModel.toUIModel() = Carrera(
    id = this.id,
    nombre = this.nombre
)

fun EstudianteModel.toUIModel() = Estudiante(
    id = this.id,
    nombre = this.nombre,
    apePaterno = this.apellidoPaterno,
    apeMaterno = this.apellidoMaterno,
    numeroTelefono = this.numeroTelefono,
    numeroControl = this.numeroControl,
    semestre = this.semestre,
    asesor = this.asesor?.let { Asesor(id = it.id) },
    admin = this.asesor?.admin?.let { Admin( id = it.id ) }
)


@Composable
fun Perfil(
    modifier: Modifier = Modifier,
    estudiante: Estudiante,
    carrera: Carrera,
    onTelefonoClick: () -> Unit,
    isEditable: Boolean = false,
    onEditarClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val apellidos = "${estudiante.apePaterno} ${estudiante.apeMaterno}"
        val carrera = carrera.nombre
        val semestre = estudiante.semestre.toOrdinal()
        val noControl = estudiante.numeroControl
        val noTelefono = estudiante.numeroTelefono

        /* Carta de nombre e informacion */
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            colors = CardDefaults.cardColors().copy(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = estudiante.nombre,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = apellidos,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.weight(1f, fill = true))
                Text(text = "$carrera, $semestre", style = MaterialTheme.typography.headlineSmall)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (estudiante.asesor != null)
                RolTag(text = "Asesor", surfaceColor = MaterialTheme.colorScheme.secondaryContainer)

            if (estudiante.admin != null)
                RolTag(text = "Admin", surfaceColor = MaterialTheme.colorScheme.secondaryContainer)
        }

        /* Informacion de contacto */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Número de Control:")
            Text(noControl)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("Número de Telefono: $noTelefono")

            Spacer(Modifier.weight(1f))
            FilledTonalIconButton(onClick = onTelefonoClick) {
                Icon(
                    imageVector = Icons.Outlined.Call,
                    contentDescription = "Llamar estudiante"
                )
            }
        }

        if (isEditable) {
            Spacer(Modifier.weight(1f, true))
            TextButton(onClick = onEditarClick) {
                Text("Editar Datos", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Composable
fun RolTag(modifier: Modifier = Modifier, text: String, surfaceColor: Color) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(100f),
        color = surfaceColor,
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = text
        )
    }
}

fun Int.toOrdinal(): String {
    val terminacion = when (this) {
        2 -> "do"
        4, 5, 6 -> "to"
        7, 10 -> "mo"
        8 -> "vo"
        in 11..19 -> "vo"
        9 -> "no"
        else -> "er"
    }
    return this.toString() + terminacion
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PerfilPreview() {
    val estudiante = Estudiante(
        id = 1,
        nombre = "Juan",
        apePaterno = "Ladrón de Guevara",
        apeMaterno = "Lopeida",
        numeroTelefono = "1800000040",
        numeroControl = "20001987",
        semestre = 7,
        asesor = null,
        admin = null
    )
    val carrera = Carrera(1, "Administración")

    AsesoriAppTheme {
        Scaffold(
            topBar = { TopAppBar(title = { Text("Pantalla de Perfil") }) }
        ) { paddingValues ->
            Perfil(
                modifier = Modifier
                    .padding(paddingValues)
                    .consumeWindowInsets(paddingValues),
                estudiante, carrera,
                isEditable = true,
                onEditarClick = {},
                onTelefonoClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun RolTagPreview() {
    AsesoriAppTheme {
        RolTag(
            surfaceColor = MaterialTheme.colorScheme.tertiaryContainer,
            text = "Asesor"
        )
    }
}