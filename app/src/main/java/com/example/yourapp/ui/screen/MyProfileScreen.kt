package com.example.yourapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.yourapp.R
import com.example.yourapp.core.MyProfile
import com.example.yourapp.ui.composable.container.RowText
import com.example.yourapp.ui.composable.container.RowTwoButtons
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
                Text(text = stringResource(id = R.string.my_profile))
            },
            actions = {
                IconButton(
                    onClick = intents.iChats,
                    content = {
                        Icon(Icons.Filled.Face, stringResource(id = R.string.clear))
                    }
                )
            },
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
                .padding(16.dp, 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.FillHeight,
                model = state.avatar,
                contentDescription = null,
            )

            RowText(
                txt1 = stringResource(id = R.string.phone),
                txt2 = state.phone,
            )
            RowText(
                txt1 = stringResource(id = R.string.nick),
                txt2 = state.name,
            )
            RowText(
                txt1 = stringResource(id = R.string.city),
                txt2 = state.city,
            )
            RowText(
                txt1 = stringResource(id = R.string.date_of_birth),
                txt2 = state.birthday,
            )

            RowText(
                txt1 = stringResource(id = R.string.zodiac_sign),
                txt2 = stringResource(id = state.zodiac),
            )
            RowText(
                txt1 = stringResource(id = R.string.status),
                txt2 = state.status,
            )
        }

        RowTwoButtons(
            txt1 = stringResource(id = R.string.log_out),
            onClick1 = intents.iLogOut,
            enabled1 = true,
            txt2 = stringResource(id = R.string.edit),
            onClick2 = intents.iEditProfile,
            enabled2 = true
        )

    }
}