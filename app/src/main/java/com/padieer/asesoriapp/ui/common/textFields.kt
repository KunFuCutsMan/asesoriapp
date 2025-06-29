package com.padieer.asesoriapp.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign

/**
 * # OutlinedTextFieldConMaximo
 *
 * Un wrapper de `OutlinedTextField` que provee limita la longitud del input `value` a un máximo
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