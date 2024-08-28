package com.example.yourapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.yourapp.R
import com.example.yourapp.core.Registration
import com.example.yourapp.ui.composable.ClearIcon
import com.example.yourapp.ui.composable.container.ErrorWait
import com.example.yourapp.ui.composable.container.KeyboardColumn
import com.example.yourapp.ui.composable.container.RowTwoButtons
import com.example.yourapp.ui.composable.textField.UserNameTextField
import com.example.yourapp.viewModel.RegistrationViewModel
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel,
    onNavigate: (Registration.Navigation) -> Unit,
    onTopBar: (topBar: @Composable () -> Unit) -> Unit,
) {

    LaunchedEffect(Unit) {
        onNavigate(viewModel.navigationFlow.first())
    }

    val state = viewModel.observeState().value
    val intents = viewModel.intents

    onTopBar {
        TopAppBar(
            title = {
                Text(text = stringResource(id = R.string.registration))
            },
            navigationIcon = {
                IconButton(onClick = intents.iCancel) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                }
            }
        )
    }

    KeyboardColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ErrorWait(
            wait = state.wait,
            error = state.error
        )

        val isErrorUserName = remember { mutableStateOf(true) }

        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp, 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(id = R.string.phone_number)) },
                value = state.phone,
                onValueChange = {},
                readOnly = true
            )

            OutlinedTextField(
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(id = R.string.enter_your_nickname)) },
                value = state.name,
                onValueChange = intents.iChangeName,
                enabled = !state.wait,
                trailingIcon = {
                    ClearIcon(
                        text = state.name,
                        enabled = !state.wait,
                        onText = intents.iChangeName
                    )
                }
            )

            UserNameTextField(
                userName = state.userName,
                enabled = !state.wait,
                isError = isErrorUserName.value,
                onError = { isErrorUserName.value = it },
                onUserName = intents.iChangeUserName
            )
        }

        RowTwoButtons(
            txt1 = stringResource(id = R.string.cancel),
            onClick1 = intents.iCancel,
            enabled1 = true,
            txt2 = stringResource(id = R.string.registration),
            onClick2 = intents.iRegister,
            enabled2 = !state.wait && !isErrorUserName.value
        )

    }

}