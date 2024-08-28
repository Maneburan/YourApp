package com.example.yourapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsScreen(
    onTopBar: (topBar: @Composable () -> Unit) -> Unit,

    toChat: (second: String, third: String) -> Unit,
    toBack: () -> Unit,
) {

    onTopBar {
        TopAppBar(
            title = {
                Text(text = "Чаты")
            },
            navigationIcon = {
                IconButton(onClick = toBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                }
            }
        )
    }

    val list: List<Triple<String, String, String>> = listOf(
        Triple("1", "Анна", "09-04-2023"),
        Triple("2", "Миша", "23-06-2022"),
        Triple("3", "Сергей", "12-07-2021"),
        Triple("4", "Иван", "08-11-2020"),
        Triple("5", "Вика", "02-09-2024"),
    )

    val listState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 8.dp),
        state = listState,
    ) {
        items(
            items = list,
            key = { it.first }
        ) {
            ListItem(
                modifier = Modifier
                    .height(64.dp)
                    .clickable {
                        toChat(it.second, it.third)
                    },
                headlineContent = {
                    Text(text = it.second)
                },
                supportingContent = {
                    Text(text = it.third)
                },
                leadingContent = {
                    AsyncImage(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(color = MaterialTheme.colorScheme.outline),
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center,
                        model = null,
                        contentDescription = null,
                    )
                }
            )
            HorizontalDivider()
        }
    }
}