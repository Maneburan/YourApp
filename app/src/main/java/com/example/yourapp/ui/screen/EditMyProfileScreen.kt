package com.example.yourapp.ui.screen

import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.yourapp.R
import com.example.yourapp.core.EditMyProfile
import com.example.yourapp.ui.composable.ClearIcon
import com.example.yourapp.ui.composable.ImagePicker
import com.example.yourapp.ui.composable.container.ErrorWait
import com.example.yourapp.ui.composable.container.KeyboardColumn
import com.example.yourapp.ui.composable.container.RowTwoButtons
import com.example.yourapp.ui.composable.container.TryAgain
import com.example.yourapp.ui.composable.textField.OutlinedTextFieldDatePicker
import com.example.yourapp.viewModel.EditMyProfileViewModel
import kotlinx.coroutines.flow.first
import java.io.ByteArrayOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMyProfileScreen(
    viewModel: EditMyProfileViewModel,
    onNavigate: (EditMyProfile.Navigation) -> Unit,
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
                Text(text = stringResource(id = R.string.edit_profile))
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

        when(state.pages) {
            EditMyProfile.Model.Pages.Edit -> {
                Edit(
                    wait = state.wait,
                    name = state.name,
                    avatar = state.avatar,
                    city = state.city,
                    vk = state.vk,
                    instagram = state.instagram,
                    status = state.status,
                    birthDay = state.birthday,

                    onName = intents.iChangeName,
                    onCity = intents.iChangeCity,
                    onVk = intents.iChangeVk,
                    onInstagram = intents.iChangeInstagram,
                    onStatus = intents.iChangeStatus,
                    onBase64 = intents.iChangeBase64,
                    onBirthDay = intents.iChangeBirthDay,

                    onEdit = intents.iEditRemoteProfile,
                    onCancel = intents.iCancel,
                )
            }
            EditMyProfile.Model.Pages.TryAgain -> {
                TryAgain(
                    wait = state.wait,
                    onTryAgain = intents.iGetRemoteProfile,
                    onCancel = intents.iCancel,
                )
            }
        }
    }
}

@Composable
private fun ColumnScope.Edit(
    wait: Boolean,
    avatar: String,
    name: String,
    birthDay: String,
    city: String,
    vk: String,
    instagram: String,
    status: String,

    onName: (String) -> Unit,
    onCity: (String) -> Unit,
    onVk: (String) -> Unit,
    onInstagram: (String) -> Unit,
    onStatus: (String) -> Unit,
    onBase64: (String?) -> Unit,
    onBirthDay: (String) -> Unit,

    onEdit: () -> Unit,
    onCancel: () -> Unit,
) {
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .weight(1f)
            .padding(16.dp, 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ImagePicker(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .height(250.dp),
            contentScale = ContentScale.FillHeight,
            remoteUri = Uri.parse(avatar)
        ) { bitmap ->
            if (bitmap == null) onBase64(null)
            else {
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                val str = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)
                onBase64(str)
            }
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(id = R.string.enter_nickname)) },
            value = name,
            onValueChange = onName,
            enabled = !wait,
            trailingIcon = {
                ClearIcon(
                    text = name,
                    enabled = !wait,
                    onText = onName
                )
            }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(id = R.string.enter_city)) },
            value = city,
            onValueChange = onCity,
            enabled = !wait,
            trailingIcon = {
                ClearIcon(
                    text = city,
                    enabled = !wait,
                    onText = onCity
                )
            }
        )

        OutlinedTextFieldDatePicker(
            label = stringResource(id = R.string.enter_birth_day),
            text = birthDay,
            onText = onBirthDay
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(id = R.string.enter_vk)) },
            value = vk,
            onValueChange = onVk,
            enabled = !wait,
            trailingIcon = {
                ClearIcon(
                    text = vk,
                    enabled = !wait,
                    onText = onVk
                )
            }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(id = R.string.enter_insta)) },
            value = instagram,
            onValueChange = onInstagram,
            enabled = !wait,
            trailingIcon = {
                ClearIcon(
                    text = instagram,
                    enabled = !wait,
                    onText = onInstagram
                )
            }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(id = R.string.enter_status)) },
            value = status,
            onValueChange = onStatus,
            enabled = !wait,
            trailingIcon = {
                ClearIcon(
                    text = status,
                    enabled = !wait,
                    onText = onStatus
                )
            }
        )
    }

    RowTwoButtons(
        txt1 = stringResource(id = R.string.cancel),
        onClick1 = onCancel,
        enabled1 = true,
        txt2 = stringResource(id = R.string.edit),
        onClick2 = onEdit,
        enabled2 = true
    )

}























