package com.padieer.asesoriapp.ui.common.search

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.padieer.asesoriapp.domain.search.Searchable
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
internal fun <T: Searchable> SearchDialog(
    modifier: Modifier = Modifier,
    label: String,
    placeholder: String,
    onSearch: (String) -> Unit,
    onDismiss: () -> Unit,
    onItemClick: (T) -> Unit,
    searchableItems: List<T>,
) {
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Card(
            modifier = modifier
                .widthIn(360.dp, 720.dp)
                .heightIn(240.dp, (screenHeight * 2 / 3).dp)
        ) {
            TextField(
                modifier = modifier.fillMaxWidth(),
                value = "",
                label = { Text(label) },
                placeholder = { Text(placeholder) },
                leadingIcon = { Icon(Icons.Outlined.Search, "") },
                onValueChange = onSearch,
            )

            HorizontalDivider()

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
            ) {
                items(searchableItems, key = { it.id }) { item ->
                    ListItem(
                        modifier = Modifier.clickable { onItemClick(item) },
                        colors = ListItemDefaults.colors(
                            containerColor = Color.Transparent
                        ),
                        headlineContent = {
                            Text(item.displayName)
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SearchDialogPreview() {
    AsesoriAppTheme(false) {
        Scaffold { paddingValues ->
            Surface(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)) {
                SearchDialog(
                    label = "Busca algo",
                    placeholder = "Buscar...",
                    onDismiss = {},
                    onItemClick = {},
                    onSearch = {},
                    searchableItems = List(20) { SearchableExample("Cosa $it", id = it) }
                )
            }
        }
    }
}

internal data class SearchableExample(
    override val displayName: String,
    override val id: Int
): Searchable