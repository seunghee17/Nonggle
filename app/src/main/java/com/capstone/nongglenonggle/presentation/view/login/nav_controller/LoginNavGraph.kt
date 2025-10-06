package com.capstone.nongglenonggle.presentation.view.login.nav_controller

import android.content.Intent
import androidx.activity.ComponentActivity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.capstone.nongglenonggle.app.navigation.Screens
import com.capstone.nongglenonggle.presentation.view.login.LoginContract
import com.capstone.nongglenonggle.presentation.view.login.LoginRoute
import com.capstone.nongglenonggle.presentation.view.login.LoginViewModel
import com.capstone.nongglenonggle.presentation.view.worker.home.WorkerMainActivity

fun NavGraphBuilder.LoginNavGraph(navHostController: NavHostController) {
    navigation(
        route = Screens.Login.route,
        startDestination = Screens.Login.LoginScreen.route,
    ) {

        composable(Screens.Login.LoginScreen.route) { entry ->
            val viewModel = hiltViewModel<LoginViewModel>()
            val context = LocalContext.current
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
            LoginRoute(
                viewModel = viewModel,
                onLaunchGoogleSignIn = { intentSender ->
                    launcher.launch(
                        IntentSenderRequest.Builder(intentSender).build()
                    )
                },
                navigateToEnrollUser = {
                        navHostController.navigate(Screens.Signup.route) {
                        popUpTo(Screens.Login.route) {inclusive = true} //로그인 그래프 통째 제거
                        launchSingleTop = true
                    }
                },
                navigateToWorkerHome = {
                    val intent = Intent(context, WorkerMainActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    }
                    context.startActivity(intent)
                },
            )
        }
    }
}