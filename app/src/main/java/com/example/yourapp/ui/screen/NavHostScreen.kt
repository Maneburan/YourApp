package com.example.yourapp.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.yourapp.core.Authorization
import com.example.yourapp.core.EditMyProfile
import com.example.yourapp.core.MyProfile
import com.example.yourapp.core.Registration
import com.example.yourapp.util.Nav

@Composable
fun NavHostScreen(
    modifier: Modifier,
    navController: NavHostController,
    startDestination: String,
    onTopBar: (topBar: @Composable () -> Unit) -> Unit,
    onBack: () -> Unit
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            route = Nav.HostRoute.MY_PROFILE
        ) {
            MyProfileScreen(
                viewModel = hiltViewModel(),
                onNavigate = { navigation ->
                    when(navigation) {
                        MyProfile.Navigation.ToAuthorization ->
                            navController.navigate(Nav.HostRoute.AUTHORIZATION)
                        MyProfile.Navigation.ToEditMyProfile ->
                            navController.navigate(Nav.HostRoute.EDIT_MY_PROFILE)
                    }
                },
                onTopBar = onTopBar
            )
        }

        composable(
            route = Nav.HostRoute.EDIT_MY_PROFILE
        ) {
            EditMyProfileScreen(
                viewModel = hiltViewModel(),
                onNavigate = { navigation ->
                    when(navigation) {
                        EditMyProfile.Navigation.ToAuthorization -> {
                            navController.popBackStack()
                            navController.navigate(Nav.HostRoute.AUTHORIZATION) {
                                popUpTo(Nav.HostRoute.AUTHORIZATION) {
                                    inclusive = true
                                }
                            }
                        }
                        EditMyProfile.Navigation.ToMyProfile ->
                            navController.navigate(Nav.HostRoute.MY_PROFILE) {
                                popUpTo(Nav.HostRoute.MY_PROFILE) {
                                    inclusive = true
                                }
                            }
                        EditMyProfile.Navigation.ToBack ->
                            navController.popBackStack()
                    }
                },
                onTopBar = onTopBar
            )
        }

        composable(
            route = Nav.HostRoute.AUTHORIZATION
        ) {
            BackHandler {
                onBack()
//                navController.navigate(Nav.HostRoute.MY_PROFILE) {
//                    popUpTo(Nav.HostRoute.MY_PROFILE) {
//                        inclusive = true
//                    }
//                }
            }
            AuthorizationScreen(
                viewModel = hiltViewModel(),
                onNavigate = { navigation ->
                    when(navigation) {
                        Authorization.Navigation.ToMyProfile ->
                            navController.navigate(Nav.HostRoute.MY_PROFILE) {
                                popUpTo(Nav.HostRoute.MY_PROFILE) {
                                    inclusive = true
                                }
                            }
                        is Authorization.Navigation.ToRegistration ->
                            navController.navigate("${Nav.HostRoute.REGISTRATION}/" +
                                    navigation.phone
                            )
                    }
                },
                onTopBar = onTopBar
            )
        }

        composable(
            route = "${Nav.HostRoute.REGISTRATION}/{${Nav.Args.PHONE}}"
        ) {
            RegistrationScreen(
                viewModel = hiltViewModel(),
                onNavigate = { navigation ->
                    when(navigation) {
                        Registration.Navigation.ToMyProfile ->
                            navController.navigate(Nav.HostRoute.MY_PROFILE) {
                                popUpTo(Nav.HostRoute.MY_PROFILE) {
                                    inclusive = true
                                }
                            }
                        Registration.Navigation.ToBack ->
                            navController.popBackStack()
                    }
                },
                onTopBar = onTopBar
            )
        }

    }

}






















