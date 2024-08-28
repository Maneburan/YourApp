package com.example.yourapp.ui.composable.container

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.yourapp.R
import com.example.yourapp.util.Model.Error

@Composable
fun ErrorWait(
    wait: Boolean,
    error: Error?,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (wait) CircularProgressIndicator()

        if (error != null) {
            val text = "${stringResource(id = R.string.error)}: ${error.msg ?: if (error.res != null) 
                stringResource(error.res) else stringResource(R.string.empty_text)}"

            Text(
                modifier = Modifier.padding(bottom = 8.dp), text = text,
                color = Color.Red
            )
        }
    }
}