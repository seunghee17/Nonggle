package com.capstone.nongglenonggle.presentation.view.signup.nav_controller
import android.content.Intent
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.capstone.nongglenonggle.app.navigation.Screens
import com.capstone.nongglenonggle.presentation.view.AddressSearchWebViewScreen
import com.capstone.nongglenonggle.presentation.view.signup.SignupAgreeTermsRoute
import com.capstone.nongglenonggle.presentation.view.signup.SignupViewModel
import com.capstone.nongglenonggle.presentation.view.signup.SignupGetFarmerInfoRoute
import com.capstone.nongglenonggle.presentation.view.signup.SignupSetUserTypeRoute
import com.capstone.nongglenonggle.presentation.view.worker.home.WorkerMainActivity

fun NavGraphBuilder.SignupNavGraph(navHostController: NavHostController) {
    navigation(
        route = Screens.Signup.route,
        startDestination = Screens.Signup.SetType.route
    ) {
        composable(route = Screens.Signup.SetType.route) { entry ->
            val parent = remember(entry) {navHostController.getBackStackEntry(Screens.Signup.route)}
            val viewModel : SignupViewModel = hiltViewModel(parent)
            SignupSetUserTypeRoute(
                navigateToStep1 = { navHostController.navigate(Screens.Signup.Step2.route) },
                viewModel = viewModel
            )
        }

        composable(route = Screens.Signup.Step2.route) { entry ->
            val parent = remember(entry) {navHostController.getBackStackEntry(Screens.Signup.route)}
            val viewModel : SignupViewModel = hiltViewModel(parent)
            val context = LocalContext.current

            SignupAgreeTermsRoute(
                viewModel = viewModel,
                navigateToStep3 = { navHostController.navigate(Screens.Signup.Step3.route) },
                navigateToHome = {
                    val intent = Intent(context, WorkerMainActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    }
                    context.startActivity(intent)
                },
                navigateToBack = { navHostController.popBackStack() }
            )
        }

        composable(route = Screens.Signup.Step3.route) { entry ->
            val parent = remember (entry) {navHostController.getBackStackEntry(Screens.Signup.route)}
            val viewModel : SignupViewModel = hiltViewModel(parent)
            val context = LocalContext.current

            SignupGetFarmerInfoRoute(
                viewModel = viewModel,
                navigateToHomeScreen = {
                    val intent = Intent(context, WorkerMainActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    }
                    context.startActivity(intent)
                },
                navigateToSearchAddress = {
                    navHostController.navigate(Screens.Signup.AddressSearch.route)
                },
                navigateToBackScreen = {
                    navHostController.popBackStack()
                },
                navHostController = navHostController
            )
        }

        composable(route = Screens.Signup.AddressSearch.route) {
            AddressSearchWebViewScreen(navHostController)
        }
    }
}