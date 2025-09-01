package com.capstone.nongglenonggle.presentation.view.worker.resume.nav_controller

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.capstone.nongglenonggle.app.Screens
import com.capstone.nongglenonggle.presentation.view.worker.resume.WorkerResumeComposeViewModel
import com.capstone.nongglenonggle.presentation.view.worker.resume.ResumeScreen

fun NavGraphBuilder.WorkerResumeGraph(navHostController: NavHostController) {
    navigation(
        route = Screens.WorkerResumeWriting.route,
        startDestination = Screens.WorkerResumeWriting.ResumeTabInputScreen.route
    ) {
        composable(route = Screens.WorkerResumeWriting.ResumeTabInputScreen.route) { entry ->
            val parent = remember(entry) {navHostController.getBackStackEntry(Screens.WorkerResumeWriting.route)}
            val viewModel : WorkerResumeComposeViewModel = hiltViewModel(parent)
            ResumeScreen(viewModel = viewModel, navController = navHostController)
        }
    }
}