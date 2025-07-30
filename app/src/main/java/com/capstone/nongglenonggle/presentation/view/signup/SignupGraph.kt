package com.capstone.nongglenonggle.presentation.view.signup

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.capstone.nongglenonggle.app.Screens

fun NavGraphBuilder.SignupNavGraph(
    navHostController: NavHostController,
) {
    navigation(
        route = Screens.Signup.route,
        startDestination = Screens.Signup.SetType.route
    ) {
        composable(route = Screens.Signup.SetType.route) {
            SetUserTypeScreen(navHostController)
        }
        composable(route = Screens.Signup.Step1.route) {
            SetUserTypeScreen(navHostController)
        }
    }
}