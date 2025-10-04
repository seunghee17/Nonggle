package com.capstone.nongglenonggle.presentation.view.farmer.notice.nav_controller

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.capstone.nongglenonggle.app.navigation.Screens
import com.capstone.nongglenonggle.presentation.view.farmer.notice.NoticeWritingFinalScreen
import com.capstone.nongglenonggle.presentation.view.farmer.notice.NoticeWritingOnBoardingScreen
import com.capstone.nongglenonggle.presentation.view.farmer.notice.notice_main.NoticeMainScreen

fun NavGraphBuilder.FarmerNoticeGraph(navHostController: NavHostController) {
    navigation(
        route = Screens.FarmerNoticeWriting.route,
        startDestination = Screens.FarmerNoticeWriting.NoticeOnBoardingScreen.route
    ) {
        composable(route = Screens.FarmerNoticeWriting.NoticeOnBoardingScreen.route) {
            NoticeWritingOnBoardingScreen()
        }

        composable(route = Screens.FarmerNoticeWriting.NoticeTabScreen.route) {
            NoticeMainScreen(navController = navHostController)
        }

        composable(route = Screens.FarmerNoticeWriting.NoticeOnBoardingScreen.route) {
            NoticeWritingFinalScreen()
        }
    }
}