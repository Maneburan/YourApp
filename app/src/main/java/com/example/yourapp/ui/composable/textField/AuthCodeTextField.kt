package com.example.yourapp.ui.composable.textField

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.yourapp.R
import com.example.yourapp.ui.composable.ClearIcon

@Composable
fun AuthCodeTxtField(
    code: String,
    enabled: Boolean,
    isError: Boolean,
    onError: (Boolean) -> Unit,
    onCode: (String) -> Unit,
) {
    val supportingText = remember { mutableIntStateOf(R.string.empty_text) }

    LaunchedEffect(code) {
        if (code.isEmpty()) {
            onError(true)
            supportingText.intValue = R.string.required_field
        } else if (code.length != 6) {
            onError(true)
            supportingText.intValue = R.string.must_characters_6
        } else {
            onError(false)
            supportingText.intValue= R.string.correct
        }
    }

    OutlinedTextField(
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        value = code,
        onValueChange = onCode,
        enabled = enabled,
        isError = isError,
        supportingText = { Text(stringResource(id = supportingText.intValue)) },
        label = { Text(stringResource(id = R.string.enter_code)) },
        trailingIcon = {
            ClearIcon(
                text = code,
                enabled = enabled,
                onText = onCode
            )
        }
    )
}