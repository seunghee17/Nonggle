package com.capstone.nongglenonggle.presentation.view.signup.nav_controller

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.capstone.nongglenonggle.app.Screens
import com.capstone.nongglenonggle.presentation.view.signup.SetUserTypeScreen
import com.capstone.nongglenonggle.presentation.view.signup.SignupComposeViewModel
import com.capstone.nongglenonggle.presentation.view.signup.SignupStep1Screen
import com.capstone.nongglenonggle.presentation.view.signup.SignupStep2Scren
import com.capstone.nongglenonggle.presentation.view.signup.SignupStep3Scren

fun NavGraphBuilder.SignupNavGraph(
    navHostController: NavHostController,
    signUpComposeViewModel: SignupComposeViewModel
) {
    navigation(
        route = Screens.Signup.route,
        startDestination = Screens.Signup.SetType.route
    ) {
        composable(route = Screens.Signup.SetType.route) {
            SetUserTypeScreen(navHostController, signUpComposeViewModel)
        }
        composable(route = Screens.Signup.Step1.route) {
            SignupStep1Screen(navHostController, signUpComposeViewModel)
        }
        composable(route = Screens.Signup.Step1.route) {
            SignupStep2Scren(navHostController, signUpComposeViewModel)
        }
        composable(route = Screens.Signup.Step1.route) {
            SignupStep3Scren(navHostController, signUpComposeViewModel)
        }
    }
}