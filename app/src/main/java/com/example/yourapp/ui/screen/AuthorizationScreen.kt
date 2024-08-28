package com.example.yourapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.yourapp.core.Authorization
import com.example.yourapp.ui.composable.container.ErrorWait
import com.example.yourapp.ui.composable.container.KeyboardColumn
import com.example.yourapp.ui.composable.container.RowTwoButtons
import com.example.yourapp.ui.composable.textField.AuthCodeTxtField
import com.example.yourapp.ui.composable.textField.PhoneTextField
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
        ErrorWait(
            wait = state.wait,
            error = state.error
        )

        val isErrorPhone = remember { mutableStateOf(true) }
        val isErrorCode = remember { mutableStateOf(true) }

        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
                .padding(16.dp, 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {

            PhoneTextField(
                number = state.phone,
                enabled = !state.wait && state.pages is Authorization.Model.Pages.SendAuthCode,
                onValueChange = { countryCode, number, isValid ->
                    intents.iChangePhone(number)
                    intents.iChangeCountryCode(countryCode)
                    isErrorPhone.value = !isValid
                },
            )

            if (state.pages is Authorization.Model.Pages.CheckAuthCode) {
                AuthCodeTxtField(
                    code = state.code,
                    enabled = !state.wait,
                    onCode = intents.iChangeCode,
                    isError = isErrorCode.value,
                    onError = { isErrorCode.value = it },
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
                Spacer(modifier = Modifier.height(16.dp))
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















