package com.padieer.asesoriapp.ui.disponibilidad

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.padieer.asesoriapp.ui.common.toHoraFormat
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme

data class Hora(
    val hora: Int,
    val ocupado: Boolean,
)


@Composable
private fun HorarioDia(
    modifier: Modifier = Modifier,
    dia: String, horario: List<Hora>,
    onHoraClick: (Int) -> Unit,
) {
    val color = MaterialTheme.colorScheme.surfaceVariant
    val colorOnSelected = MaterialTheme.colorScheme.tertiary

    Column(modifier = modifier) {
        Text(
            text = dia,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            itemsIndexed(horario) { index, it ->
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (it.ocupado) colorOnSelected else color)
                        .clickable(onClick = { onHoraClick(index) }),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Text(
                        text = it.hora.toHoraFormat(),
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = if (it.ocupado) contentColorFor(colorOnSelected) else contentColorFor(color)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun HorarioDiaPreview() {
    val horas = (7..20).toList().map {
        Hora(it, it % 3 == 0)
    }
    AsesoriAppTheme(false) {
        Surface {
            HorarioDia(
                dia = "Primario",
                horario = horas,
                onHoraClick = {},
            )
        }
    }
}