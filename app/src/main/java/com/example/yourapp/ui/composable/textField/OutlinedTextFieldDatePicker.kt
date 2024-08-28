@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.yourapp.ui.composable.textField

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.yourapp.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

const val DATA_FORMAT = "yyyy-MM-dd"//"MMM dd, yy" 2024-08-30

@Composable
fun OutlinedTextFieldDatePicker(
    modifier: Modifier = Modifier,
    label: String,
    text: String,
    onText: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardActions: KeyboardActions = KeyboardActions.Default
) {
    val time = try {
        val l = SimpleDateFormat(DATA_FORMAT, Locale.ENGLISH).parse(text)?.time
        if (l != null) TimeZone.getDefault().getOffset(l) + l else null
    }  catch (e: ParseException) {
        null
    }

    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = time)

    val openDialog = remember { mutableStateOf(false) }
    if (openDialog.value) {
        DatePickerDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false

                        val l = datePickerState.selectedDateMillis
                        val t = if (l != null) l - TimeZone.getDefault().getOffset(l) + 1L
                        else null

                        val s = try {
                            SimpleDateFormat(DATA_FORMAT, Locale.ENGLISH).format(t)
                        } catch (e: IllegalArgumentException) {
                            null
                        }

                        onText(s ?: "")
                    },
                    enabled = datePickerState.selectedDateMillis != null,
                    content = {
                        Text("OK")
                    }
                )
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    },
                    content = {
                        Text(stringResource(id = R.string.cancel))
                    }
                )
            },
            content = {
                DatePicker(state = datePickerState)
            }
        )
    }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        value = text,
        onValueChange = onText,
        label = { Text(label) },
        interactionSource = remember { MutableInteractionSource() }
            .also { interactionSource ->
                LaunchedEffect(interactionSource) {
                    interactionSource.interactions.collect {
                        if (it is PressInteraction.Release) {
                            openDialog.value = true
                        }
                    }
                }
            },
        readOnly = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = onKeyboardActions,
    )
}