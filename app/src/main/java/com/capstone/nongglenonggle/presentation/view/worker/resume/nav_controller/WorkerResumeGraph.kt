package com.capstone.nongglenonggle.presentation.view.worker.resume.nav_controller

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.capstone.nongglenonggle.app.navigation.Screens
import com.capstone.nongglenonggle.presentation.view.worker.resume.main_screen.ResumeMainViewModel
import com.capstone.nongglenonggle.presentation.view.worker.resume.main_screen.ResumeTabScreen
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step1.ResumeStep1ViewModel
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step2.ResumeStep2ViewModel
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step3.ResumeStep3ViewModel
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4.ResumeStep4ViewModel

fun NavGraphBuilder.WorkerResumeGraph(navHostController: NavHostController) {
    navigation(
        route = Screens.WorkerResumeWriting.route,
        startDestination = Screens.WorkerResumeWriting.ResumeTabInputScreen.route
    ) {
        composable(route = Screens.WorkerResumeWriting.ResumeTabInputScreen.route) { entry ->
            val parent =
                remember(entry) { navHostController.getBackStackEntry(Screens.WorkerResumeWriting.route) }
            val mainViewModel: ResumeMainViewModel = hiltViewModel(parent)
            val step1viewModel: ResumeStep1ViewModel = hiltViewModel(parent)
            val step2viewModel: ResumeStep2ViewModel = hiltViewModel(parent)
            val step3viewModel: ResumeStep3ViewModel = hiltViewModel(parent)
            val step4viewModel: ResumeStep4ViewModel = hiltViewModel(parent)
            ResumeTabScreen(
                navController = navHostController,
                viewModel = mainViewModel,
                step1ViewModel = step1viewModel,
                step2ViewModel = step2viewModel,
                step3ViewModel = step3viewModel,
                step4ViewModel = step4viewModel,
            )
        }
    }
}