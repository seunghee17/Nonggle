package com.capstone.nongglenonggle.presentation.view.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val googleAuthClient by lazy {
        GoogleAuthClient(
            context = applicationContext,
            onTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "login") {
                composable("login") {
                    val viewModel = hiltViewModel<LoginViewModel>()

                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.StartIntentSenderForResult(),
                        onResult = { result ->
                            if (result.resultCode == RESULT_OK) {
                                lifecycleScope.launch {
                                    val signInResult = googleAuthClient.signInWithIntent(
                                        intent = result.data ?: return@launch,
                                    )
                                    viewModel.onSingInResult(signInResult)
                                }
                            }
                        }
                    )

                    LoginScreen(
                        viewModel = viewModel,
                        onSignInClick = {
                            lifecycleScope.launch {
                                val signInIntentSender = googleAuthClient.signIn()
                                launcher.launch(
                                    IntentSenderRequest.Builder(signInIntentSender ?: return@launch)
                                        .build()
                                )
                            }
                        }
                    )
                }
            }
        }

    }

}
