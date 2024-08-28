package com.example.yourapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.yourapp.ui.screen.NavHostScreen
import com.example.yourapp.ui.theme.YourAppTheme
import com.example.yourapp.util.Nav
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        application

        setContent {
            navController = rememberNavController()

            val topBar: MutableState<@Composable () -> Unit> = remember { mutableStateOf( {} ) }

            YourAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize().imePadding(),
                    topBar = topBar.value
                ) { innerPadding ->

                    NavHostScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        navController = navController,
                        startDestination = Nav.HostRoute.MY_PROFILE,
                        onTopBar = { topBar.value = it },
                        onBack = {
                            finish()
                        }
                    )

                }
            }
        }
    }
}