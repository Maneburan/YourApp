package com.example.yourapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.yourapp.core.Registration
import com.example.yourapp.ui.view.KeyboardColumn
import com.example.yourapp.ui.view.ValidatedOutlinedTextField
import com.example.yourapp.util.userNameValidate
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
                Text(text = "Регистрация")
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
        if (state.wait) CircularProgressIndicator()

        if (state.error != null) {
            Text(
                modifier = Modifier.padding(bottom = 8.dp), text = "${state.error}",
                color = Color.Red
            )
        }

        val isErrorUserName = remember { mutableStateOf(true) }

        Column(
            Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Номер телефона") },
                value = state.phone,
                onValueChange = {},
                enabled = false
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Введите ваш ник") },
                value = state.name,
                onValueChange = intents.iChangeName,
                enabled = !state.wait
            )

            ValidatedOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                text = state.userName,
                isError = isErrorUserName.value,
                label = "Введите имя",
                validate = userNameValidate,
                onText = intents.iChangeUserName,
                onValidate = { isErrorUserName.value = it },
                enabled = !state.wait
            )
        }

        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = intents.iCancel,
                enabled = !state.wait
            ) {
                Text("Отмена")
            }

            Button(
                onClick = intents.iRegister,
                content = {
                    Text("Регистрация")
                },
                enabled = !state.wait && !isErrorUserName.value
            )
        }

    }

}