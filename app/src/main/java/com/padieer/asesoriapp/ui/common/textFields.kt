package com.padieer.asesoriapp.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign

/**
 * # OutlinedTextFieldConMaximo
 *
 * Un wrapper de [OutlinedTextField] que provee limita la longitud del input `value` a un máximo
 * de `maxLength`. La longitud se muestra mediante un texto soporte.
 *
 * Se asume que el callback `onValueChange` es responsable de cambiar el valor de `value`.
 *
 * @param value El valor de `OutlinedTextField`
 * @param onValueChange Callback que realiza el cambio de `value`
 * @param maxLength Longitud máxima de `value`
 */
@Composable
fun OutlinedTextFieldConMaximo(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    maxLength: Int,
    keyboardType: KeyboardType = KeyboardType.Text,
    label: @Composable () -> Unit = @Composable {},
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = value,
        modifier = modifier,
        label = label,
        singleLine = true,
        supportingText = {
            Row (
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.End
            ) {
                Text("${value.length}/${maxLength}", textAlign = TextAlign.End)
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Next
        ),
        onValueChange = {
            if ( it.length <= maxLength )
                onValueChange(it)
        },
        visualTransformation = visualTransformation
    )
}

/**
 * # OutlinedDropdown
 *
 * Una implementación de [ExposedDropdownMenuBox] que genera valores a partir de una lista `data`.
 *
 * El valor tomado en `onValueChange` es aquel presente en el `TextField` que se encuentra en el
 * elemento composable, e inicialmente será el primer elemento de la lista `data`.
 *
 * @see <ul>
 *     <li><a href="https://www.youtube.com/watch?v=_lee9vN1FiE">Tutorial original</a></li>
 *     <li><a href="https://stackoverflow.com/questions/67111020/exposed-drop-down-menu-for-jetpack-compose">ExposedDropdownMenu for Jetpack Compose</a></li>
 *     </ul>
 *
 * @param data Lista de datos
 * @param onValueChange Callback que regresa el valor seleccionado
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedDropdown(
        modifier: Modifier = Modifier,
        data: List<String>,
        onValueChange: (String) -> Unit,
        label: @Composable () -> Unit = {}) {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedValue by remember { mutableStateOf(data[0]) }

    LaunchedEffect(true) { onValueChange(data[0]) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded },
    ) {
        OutlinedTextField(
            selectedValue,
            onValueChange = {},
            readOnly = true,
            label = label,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon( isExpanded ) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = modifier.menuAnchor(type = MenuAnchorType.PrimaryNotEditable, enabled = true)
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            data.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = {
                        selectedValue = it
                        isExpanded = false
                        onValueChange(selectedValue)
                    }
                )
            }
        }
    }
}

@Composable
fun ErrorText(text: String) {
    Text(text, modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.error,
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.labelMedium
    )
}