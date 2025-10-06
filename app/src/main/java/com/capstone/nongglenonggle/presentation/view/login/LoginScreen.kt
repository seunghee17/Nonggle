package com.capstone.nongglenonggle.presentation.view.login

import android.content.IntentSender
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.nongglenonggle.core.design_system.NongleTheme
import kotlinx.coroutines.flow.collectLatest
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.capstone.nongglenonggle.presentation.view.login.component.AppLogoForLogin
import com.capstone.nongglenonggle.presentation.view.login.component.googleLoginButton
import com.capstone.nongglenonggle.presentation.view.login.component.kakaoLoginButton
import com.capstone.nongglenonggle.presentation.view.login.LoginContract.Event as LoginEvent
import com.capstone.nongglenonggle.presentation.view.login.LoginContract.Effect as LoginEffect

@Composable
internal fun LoginRoute(
    viewModel: LoginViewModel,
    onLaunchGoogleSignIn: (IntentSender) -> Unit,
    navigateToEnrollUser: () -> Unit,
    navigateToWorkerHome: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect
    val context = LocalContext.current

    LaunchedEffect(true) {
        effectFlow.collectLatest { effect ->
            when (effect) {
                is LoginEffect.NavigateToEnrollUser -> navigateToEnrollUser()

                is LoginEffect.NavigateToWorkerHome -> navigateToWorkerHome()

                is LoginEffect.UnAvailableToastmessage -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                is LoginEffect.LaunchGoogleSignIn -> {
                    onLaunchGoogleSignIn(effect.intentSender)
                }
            }
        }
    }

    LaunchedEffect(key1 = uiState.signInState.signInError) {
        uiState.signInState.signInError?.let { error ->
            if (!uiState.signInState.signInError.isNullOrEmpty()) {
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            }
        }
    }

    LoginScreen(
        onEvent = viewModel::setEvent
    )

}

@Composable
fun LoginScreen(
    onEvent: (LoginEvent) -> Unit
) {
    val context = LocalContext.current

    NongleTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            AppLogoForLogin()
            Spacer(modifier = Modifier.weight(1f))
            kakaoLoginButton(onClick = {
                onEvent(LoginEvent.KakaoLoginButtonClick)
            }, context)
            Spacer(modifier = Modifier.height(16.dp))
            googleLoginButton(onClick = {
                onEvent(LoginEvent.GoogleLoginButtonClick)
            }, context)
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onEvent = {}
    )
}
