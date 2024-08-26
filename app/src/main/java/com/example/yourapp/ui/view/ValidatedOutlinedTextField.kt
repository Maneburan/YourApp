package com.example.yourapp.ui.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.yourapp.R
import com.example.yourapp.util.ValidateModel

@Composable
fun ValidatedOutlinedTextField(
    modifier: Modifier = Modifier,
    text: String,
    isError: Boolean,
    label: String,
    maxLines: Int = 1,
    minLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardActions: KeyboardActions = KeyboardActions.Default,
    validate: (String)-> ValidateModel,
    onText: (String)->Unit,
    onValidate: (Boolean)->Unit,
    enabled: Boolean = true,
    existName: Boolean? = null
) {
    val supporting = remember { mutableIntStateOf(R.string.empty_text) }

    LaunchedEffect(text) {
        val validateModel = validate(text)
        if(existName == true && !validateModel.isError) {
            supporting.intValue = R.string.already_exist
            onValidate(true)
        }
        else {
            supporting.intValue = validateModel.supportingR
            onValidate(validateModel.isError)
        }
    }

    OutlinedTextField(
        maxLines = maxLines,
        minLines = minLines,
        singleLine = true,
        isError = isError,
        trailingIcon = {
            if(text.isNotEmpty()) {
                IconButton(onClick = { onText("") }) {
                    Icon(Icons.Filled.Clear, stringResource(id = R.string.clear))
                }
            }
        },
        modifier = modifier.fillMaxWidth(),
        value = text,
        supportingText = { Text(stringResource(id = supporting.intValue)) },
        onValueChange = { onText(it)  },
        label = { Text(label) },
        keyboardOptions = keyboardOptions,
        keyboardActions = onKeyboardActions,
        enabled = enabled
    )

}