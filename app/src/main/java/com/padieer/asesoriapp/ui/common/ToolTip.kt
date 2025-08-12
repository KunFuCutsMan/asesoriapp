package com.padieer.asesoriapp.ui.common

import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolTipWithInfo(modifier: Modifier = Modifier, title: String?, text: String) {
    val state = rememberTooltipState(isPersistent = true)
    val coroutineScope = rememberCoroutineScope()
    TooltipBox(
        modifier = modifier,
        positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(),
        state = state,
        tooltip = {
            RichTooltip(
                title = { if (title != null) Text(title) },
            ) {
                Text(text, modifier = Modifier.widthIn(50.dp, 250.dp))
            }
        }
    ) {
        IconButton(
            onClick = { coroutineScope.launch { state.show() } }
        ) {
            Icon(if (state.isVisible) Icons.Filled.Info else Icons.Outlined.Info, "Info")
        }
    }
}