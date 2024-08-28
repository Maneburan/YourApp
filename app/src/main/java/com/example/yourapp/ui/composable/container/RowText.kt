package com.example.yourapp.ui.composable.container

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun RowText(
    txt1: String,
    txt2: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$txt1: ",
            fontWeight = FontWeight(750),
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier.fillMaxSize(),
            text = txt2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}