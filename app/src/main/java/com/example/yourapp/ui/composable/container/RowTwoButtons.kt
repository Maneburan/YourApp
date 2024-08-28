package com.example.yourapp.ui.composable.container

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RowTwoButtons(
    txt1: String,
    onClick1: () -> Unit,
    enabled1: Boolean,
    txt2: String,
    onClick2: () -> Unit,
    enabled2: Boolean,
) {
    Row(modifier = Modifier
        .fillMaxWidth().padding(16.dp, 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedButton(
            onClick = onClick1,
            enabled = enabled1
        ) {
            Text(txt1)
        }

        Button(
            onClick = onClick2,
            content = {
                Text(txt2)
            },
            enabled = enabled2
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}