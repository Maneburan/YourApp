package com.example.yourapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.yourapp.R
import com.example.yourapp.core.Authorization
import com.example.yourapp.ui.view.KeyboardColumn
import com.example.yourapp.ui.view.RowTwoButtons
import com.example.yourapp.ui.view.ValidatedOutlinedTextField
import com.example.yourapp.util.codeValidate
import com.example.yourapp.util.phoneValidate
import com.example.yourapp.viewModel.AuthorizationViewModel
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorizationScreen(
    viewModel: AuthorizationViewModel,
    onNavigate: (Authorization.Navigation) -> Unit,
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
                Text(text = stringResource(id = R.string.authorization))
            },
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

        val isErrorPhone = remember { mutableStateOf(true) }
        val isErrorCode = remember { mutableStateOf(true) }

        Column(
            Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {

            ValidatedOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                text = state.phone,
                isError = isErrorPhone.value,
                label = stringResource(id = R.string.enter_phone),
                validate = phoneValidate,
                onText = intents.iChangePhone,
                onValidate = { isErrorPhone.value = it },
                enabled = !state.wait && state.pages is Authorization.Model.Pages.SendAuthCode
            )

            if (state.pages is Authorization.Model.Pages.CheckAuthCode) {
                ValidatedOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                    text = state.code,
                    isError = isErrorCode.value,
                    label = stringResource(id = R.string.enter_code),
                    validate = codeValidate,
                    onText = intents.iChangeCode,
                    onValidate = { isErrorCode.value = it },
                    enabled = !state.wait
                )
            }
        }

        when(state.pages) {
            Authorization.Model.Pages.SendAuthCode -> {
                Button(
                    onClick = intents.iSendAuthCode,
                    content = {
                        Text(stringResource(id = R.string.get_code))
                    },
                    enabled = !state.wait && !isErrorPhone.value
                )
            }
            Authorization.Model.Pages.CheckAuthCode -> {
                RowTwoButtons(
                    txt1 = stringResource(id = R.string.get_code_again),
                    onClick1 = intents.iToSendAuthCode,
                    enabled1 = !state.wait,
                    txt2 = stringResource(id = R.string.send),
                    onClick2 = intents.iAuthorize,
                    enabled2 = !state.wait && !isErrorCode.value
                )
            }
        }

    }
}















