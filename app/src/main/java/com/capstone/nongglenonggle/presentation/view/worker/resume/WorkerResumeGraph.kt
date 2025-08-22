package com.capstone.nongglenonggle.presentation.view.worker.resume

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.capstone.nongglenonggle.app.Screens

fun NavGraphBuilder.WorkerResumeGraph(navHostController: NavHostController) {
    navigation(
        route = Screens.WorkerResumeWriting.route,
        startDestination = Screens.WorkerResumeWriting.ResumeStep1.route
    ) {

    }
}