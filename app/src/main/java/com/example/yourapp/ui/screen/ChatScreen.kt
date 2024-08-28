package com.example.yourapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourapp.R
import com.example.yourapp.ui.composable.container.KeyboardColumn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    second: String,
    third: String,
    onTopBar: (topBar: @Composable () -> Unit) -> Unit,
    toBack: () -> Unit,
) {

    onTopBar {
        TopAppBar(
            title = {
                Column {
                    Text(text = second)
                    Text(text = third, fontSize = 14.sp, fontWeight = FontWeight(350))
                }
            },
            navigationIcon = {
                IconButton(onClick = toBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                }
            }
        )
    }

    KeyboardColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
                .padding(16.dp, 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {  },
                content = {
                    Text(modifier = Modifier.fillMaxWidth().padding(16.dp, 8.dp), text = "Some vendors may process your personal data on the basis of legitimate interest, which you can object to by managing your options below. Look for a link at the bottom of this page to manage or withdraw consent in privacy and cookie settings.")
                }
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {  },
                content = {
                    Text(modifier = Modifier.fillMaxWidth().padding(16.dp, 8.dp), text = "Some vendors may process your personal data on the basis of legitimate interest, which you can object to by managing your options below. Look for a link at the bottom of this page to manage or withdraw consent in privacy and cookie settings.")
                }
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {  },
                content = {
                    Text(modifier = Modifier.fillMaxWidth().padding(16.dp, 8.dp), text = "Some vendors may process your personal data on the basis of legitimate interest, which you can object to by managing your options below. Look for a link at the bottom of this page to manage or withdraw consent in privacy and cookie settings.")
                }
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {  },
                content = {
                    Text(modifier = Modifier.fillMaxWidth().padding(16.dp, 8.dp), text = "Some vendors may process your personal data on the basis of legitimate interest, which you can object to by managing your options below. Look for a link at the bottom of this page to manage or withdraw consent in privacy and cookie settings.")
                }
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {  },
                content = {
                    Text(modifier = Modifier.fillMaxWidth().padding(16.dp, 8.dp), text = "Some vendors may process your personal data on the basis of legitimate interest, which you can object to by managing your options below. Look for a link at the bottom of this page to manage or withdraw consent in privacy and cookie settings.")
                }
            )
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp),
        ) {
            val text = remember { mutableStateOf("") }

            OutlinedTextField(
                maxLines = 4,
                modifier = Modifier.weight(1f),
                value = text.value,
                onValueChange = { text.value = it },
            )

            IconButton(
                onClick = {},
                content = {
                    Icon(Icons.AutoMirrored.Filled.Send, stringResource(id = R.string.clear))
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

    }

}