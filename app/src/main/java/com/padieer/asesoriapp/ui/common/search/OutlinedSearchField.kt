package com.padieer.asesoriapp.ui.common.search

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import com.padieer.asesoriapp.domain.search.Searchable
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme

@Composable
fun <T: Searchable> OutlinedSearchField(
    modifier: Modifier = Modifier,
    label: String,
    placeholder: String = "Buscar...",
    searchables: List<T> = emptyList(),
    onValueChange: (T) -> Unit = {}) {

    var dialogState by remember { mutableStateOf(false) }
    var selectedItemText by remember { mutableStateOf("") }

    OutlinedTextField(
        value = selectedItemText,
        readOnly = true,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        onValueChange = {},
        trailingIcon = { Icon(Icons.Outlined.Search, "") },
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(selectedItemText) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    waitForUpOrCancellation(pass = PointerEventPass.Initial)?.let {
                        dialogState = true
                    }
                }
            },
    )

    if (dialogState) {
        SearchDialog(
            label = label,
            placeholder = placeholder,
            searchableItems = searchables,
            onDismiss = { dialogState = false },
            onItemClick = {
                selectedItemText = it.displayName
                dialogState = false
                onValueChange(it)
            },
        )
    }
}

@Preview
@Composable
private fun OutlinedSearchFieldPreview() {
    AsesoriAppTheme {
        Surface {
            OutlinedSearchField(
                label = "Buscar algo",
                searchables = emptyList<SearchableExample>(),
                onValueChange = {},
            )
        }
    }
}