package com.capstone.nongglenonggle.presentation.view.login.nav_controller

import androidx.activity.ComponentActivity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.capstone.nongglenonggle.app.Screens
import com.capstone.nongglenonggle.presentation.view.login.LoginContract
import com.capstone.nongglenonggle.presentation.view.login.LoginScreen
import com.capstone.nongglenonggle.presentation.view.login.LoginViewModel

fun NavGraphBuilder.LoginNavGraph(navHostController: NavHostController) {
    navigation(
        route = Screens.Signup.route,
        startDestination = Screens.Login.route
    ) {
        composable(Screens.Login.route) { entry ->
            val viewModel = hiltViewModel<LoginViewModel>()

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == RESULT_OK) {
                        result.data?.let {
                            viewModel.setEvent(LoginContract.Event.OnGoogleSignInResult(it))
                        }
                    }
                }
            )

            LoginScreen(
                viewModel = viewModel,
                onLaunchGoogleSignIn = { intentSender ->
                    launcher.launch(
                        IntentSenderRequest.Builder(intentSender).build()
                    )
                },
                navController = navHostController
            )
        }
    }
}