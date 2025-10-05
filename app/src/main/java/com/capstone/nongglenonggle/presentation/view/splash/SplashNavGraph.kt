package com.capstone.nongglenonggle.presentation.view.splash

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.capstone.nongglenonggle.app.navigation.Screens

fun NavGraphBuilder.SplashNavGraph() {
    navigation(
        route = Screens.Splash.route,
        startDestination = Screens.Splash.route
    ) {
        composable(Screens.Splash.route) { entry ->
            val viewModel = hiltViewModel<SplashViewModel>()
            SpalashScreen(viewModel = viewModel)
        }
    }
}