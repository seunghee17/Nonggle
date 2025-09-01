package com.capstone.nongglenonggle.presentation.view.signup.nav_controller
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.capstone.nongglenonggle.app.Screens
import com.capstone.nongglenonggle.presentation.view.signup.FarmerAddressSearchScreen
import com.capstone.nongglenonggle.presentation.view.signup.SetUserTypeScreen
import com.capstone.nongglenonggle.presentation.view.signup.SignupViewModel
import com.capstone.nongglenonggle.presentation.view.signup.SignupAgreeTermsScreen
import com.capstone.nongglenonggle.presentation.view.signup.SignupGetFarmerInfoScreen

fun NavGraphBuilder.SignupNavGraph(navHostController: NavHostController) {
    navigation(
        route = Screens.Signup.route,
        startDestination = Screens.Signup.SetType.route
    ) {
        composable(route = Screens.Signup.SetType.route) { entry ->
            val parent = remember(entry) {navHostController.getBackStackEntry(Screens.Signup.route)}
            val viewModel : SignupViewModel = hiltViewModel(parent)
            SetUserTypeScreen(navHostController, viewModel)
        }

        composable(route = Screens.Signup.Step2.route) { entry ->
            val parent = remember(entry) {navHostController.getBackStackEntry(Screens.Signup.route)}
            val viewModel : SignupViewModel = hiltViewModel(parent)
            SignupAgreeTermsScreen(navHostController, viewModel)
        }
        composable(route = Screens.Signup.Step3.route) { entry ->
            val parent = remember(entry) {navHostController.getBackStackEntry(Screens.Signup.route)}
            val viewModel : SignupViewModel = hiltViewModel(parent)
            SignupGetFarmerInfoScreen(navHostController, viewModel)
        }
        composable(route = Screens.Signup.AddressSearchWebView.route) { entry ->
            val parent = remember(entry) {navHostController.getBackStackEntry(Screens.Signup.route)}
            val viewModel : SignupViewModel = hiltViewModel(parent)
            FarmerAddressSearchScreen(navHostController, viewModel)
        }
    }
}