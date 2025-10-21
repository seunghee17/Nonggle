package com.capstone.nongglenonggle.presentation.view.splash

import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.capstone.nongglenonggle.app.navigation.Screens
import com.capstone.nongglenonggle.presentation.view.worker.home.WorkerMainActivity

fun NavGraphBuilder.SplashNavGraph(navHostController: NavHostController) {
    navigation(
        route = Screens.Splash.route,
        startDestination = Screens.Splash.SplashScreen.route
    ) {
        composable(Screens.Splash.SplashScreen.route) { entry ->
            val viewModel = hiltViewModel<SplashViewModel>()
            val context = LocalContext.current

            SplashRoute(
                viewModel = viewModel,
                navigateToWorkerHome = {
                    val intent = Intent(context, WorkerMainActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    }
                    context.startActivity(intent)
                },
                navigateToLogin = {
                    navHostController.navigate(Screens.Login.route) {
                        popUpTo(Screens.Splash.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}