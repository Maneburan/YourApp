package com.example.yourapp.ui.composable.textField

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.yourapp.R
import com.example.yourapp.ui.composable.ClearIcon

@Composable
fun UserNameTextField(
    userName: String,
    enabled: Boolean,
    isError: Boolean,
    onError: (Boolean) -> Unit,
    onUserName: (String) -> Unit,
) {

    val supportingText = remember { mutableIntStateOf(R.string.empty_text) }

    LaunchedEffect(userName) {
        if (userName.isEmpty()) {
            onError(true)
            supportingText.intValue = R.string.required_field
        }  else if (userName.length < 5) {
            onError(true)
            supportingText.intValue = R.string.must_characters_5
        } else if (!"[A-Za-z0-9-_]+".toRegex().matches(userName)) {
            onError(true)
            supportingText.intValue = R.string.not_match
        } else {
            onError(false)
            supportingText.intValue= R.string.correct
        }
    }

    OutlinedTextField(
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        value = userName,
        onValueChange = onUserName,
        enabled = enabled,
        isError = isError,
        supportingText = { Text(stringResource(id = supportingText.intValue)) },
        label = { Text(stringResource(id = R.string.enter_username)) },
        trailingIcon = {
            ClearIcon(
                text = userName,
                enabled = enabled,
                onText = onUserName
            )
        }
    )
}