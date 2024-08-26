package com.example.yourapp.ui.screen

import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.yourapp.R
import com.example.yourapp.core.EditMyProfile
import com.example.yourapp.ui.view.ImagePicker
import com.example.yourapp.ui.view.KeyboardColumn
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
        if (state.wait) CircularProgressIndicator()

        if (state.error != null) {
            Text(modifier = Modifier.padding(bottom = 8.dp), text = "${state.error}",
                color = Color.Red)
        }

        when(state.pages) {
            EditMyProfile.Model.Pages.Edit -> {
                Edit(
                    wait = state.wait,
                    name = state.name,
                    avatar = state.avatar,
                    username = state.username,
                    city = state.city,
                    vk = state.vk,
                    instagram = state.instagram,
                    status = state.status,

                    onName = intents.iChangeName,
                    onCity = intents.iChangeCity,
                    onVk = intents.iChangeVk,
                    onInstagram = intents.iChangeInstagram,
                    onStatus = intents.iChangeStatus,
                    onBase64 = intents.iChangeBase64,

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
private fun TryAgain(
    wait: Boolean,
    onTryAgain: () -> Unit,
    onCancel: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = onCancel,
            ) {
                Text("Отмена")
            }

            Button(
                onClick = onTryAgain,
                enabled = !wait
            ) {
                Text("Try Again")
            }
        }
    }
}

@Composable
private fun ColumnScope.Edit(
    wait: Boolean,
    avatar: String,
    name: String,
    username: String,
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

    onEdit: () -> Unit,
    onCancel: () -> Unit,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .weight(1f),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        ImagePicker(remoteUri = Uri.parse(avatar)) { bitmap ->
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
            label = { Text("Введите ник") },
            value = name,
            onValueChange = onName,
            enabled = !wait
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Имя") },
            value = username,
            onValueChange = {},
            enabled = false
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Введите город") },
            value = city,
            onValueChange = onCity,
            enabled = !wait
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Введите вк") },
            value = vk,
            onValueChange = onVk,
            enabled = !wait
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Введите инсту") },
            value = instagram,
            onValueChange = onInstagram,
            enabled = !wait
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Введите статус") },
            value = status,
            onValueChange = onStatus,
            enabled = !wait
        )
    }

    Row(modifier = Modifier
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedButton(
            onClick = onCancel,
        ) {
            Text("Отмена")
        }

        Button(
            onClick = onEdit,
            enabled = !wait
        ) {
            Text("Edit")
        }
    }
}























