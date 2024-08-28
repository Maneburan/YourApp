package com.example.yourapp.ui.composable.container

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.yourapp.R

@Composable
fun TryAgain(
    wait: Boolean,
    onTryAgain: () -> Unit,
    onCancel: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        RowTwoButtons(
            txt1 = stringResource(id = R.string.cancel),
            onClick1 = onCancel,
            enabled1 = true,
            txt2 = stringResource(id = R.string.try_again),
            onClick2 = onTryAgain,
            enabled2 = !wait
        )
    }
}