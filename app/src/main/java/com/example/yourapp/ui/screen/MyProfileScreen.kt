package com.example.yourapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.yourapp.core.MyProfile
import com.example.yourapp.viewModel.MyProfileViewModel
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfileScreen(
    viewModel: MyProfileViewModel,
    onNavigate: (MyProfile.Navigation) -> Unit,
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
                Text(text = "Мой профиль")
            },
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = state.avatar,
                contentDescription = null,
            )

            RowText(
                txt1 = "Телефон",
                txt2 = state.phone,
            )
            RowText(
                txt1 = "Ник",
                txt2 = state.name,
            )
            RowText(
                txt1 = "Город",
                txt2 = state.city,
            )
            RowText(
                txt1 = "Статус",
                txt2 = state.status,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(onClick = intents.iLogOut) {
                Text("Log out")
            }

            Button(onClick = intents.iEditProfile) {
                Text("Edit")
            }
        }

    }
}

@Composable
private fun RowText(
    txt1: String,
    txt2: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$txt1: ",
            fontWeight = FontWeight(750)
        )
        Text(
            modifier = Modifier.fillMaxSize(),
            text = txt2
        )
    }
}