package com.example.yourapp.ui.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.yourapp.R

@Composable
fun ClearIcon(
    text: String,
    onText: (String) -> Unit,
    enabled: Boolean
) {
    if(text.isNotEmpty()) {
        IconButton(
            enabled = enabled,
            onClick = { onText("") },
            content = { Icon(Icons.Filled.Clear, stringResource(id = R.string.clear)) },
        )
    }
}