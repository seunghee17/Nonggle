package com.capstone.nongglenonggle.presentation.view.login

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.common.button.ImageButton
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.core.design_system.NongleTheme
import com.capstone.nongglenonggle.core.design_system.soYo
import kotlinx.coroutines.flow.collectLatest
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.capstone.nongglenonggle.app.navigation.Screens
import com.capstone.nongglenonggle.presentation.view.farmer.home.MainActivity
import com.capstone.nongglenonggle.presentation.view.worker.home.WorkerMainActivity

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navController: NavHostController,
    onLaunchGoogleSignIn: (IntentSender) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect
    val context = LocalContext.current

    LaunchedEffect(true) {
        effectFlow.collectLatest { effect ->
            when (effect) {
                is LoginContract.Effect.NavigateToEnrollUser -> {
                    navController.navigate(Screens.Signup.route) {
                        popUpTo(Screens.Login.route) {inclusive = true} //로그인 그래프 통째 제거
                        launchSingleTop = true
                    }

                }

                is LoginContract.Effect.NavigateToFarmerHome -> {
                    val intent = Intent(context, MainActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    }
                    context.startActivity(intent)
                }

                is LoginContract.Effect.NavigateToWorkerHome -> {
                    val intent = Intent(context, WorkerMainActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    }
                    context.startActivity(intent)
                }

                is LoginContract.Effect.UnAvailableToastmessage -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                is LoginContract.Effect.LaunchGoogleSignIn -> {
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

    NongleTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            AppLogoForLogin()
            Spacer(modifier = Modifier.weight(1f))
            kakaoLoginButton(onClick = {
                viewModel.setEvent(LoginContract.Event.KakaoLoginButtonClick)
            }, context)
            Spacer(modifier = Modifier.height(16.dp))
            googleLoginButton(onClick = {
                viewModel.setEvent(LoginContract.Event.GoogleLoginButtonClick)
            }, context)
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun AppLogoForLogin() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp),
        text = "농글",
        color = NonggleTheme.colors.m1,
        style = TextStyle(
            fontFamily = soYo,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        ),
        textAlign = TextAlign.Center
    )
}

@Composable
fun kakaoLoginButton(
    onClick: () -> Unit,
    context: Context
) {
    ImageButton(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        onClick = onClick,
        titleText = context.getString(R.string.start_with_kakao),
        contentColor = NonggleTheme.colors.g1,
        backgroundColor = Color(0xFFF9E000),
        titleTextStyle = NonggleTheme.typography.b4_btn,
        imageResource = R.drawable.kakaobtn
    )
}

@Composable
fun googleLoginButton(
    onClick: () -> Unit,
    context: Context
) {
    ImageButton(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        onClick = onClick,
        titleText = context.getString(R.string.start_with_kakao),
        contentColor = NonggleTheme.colors.g2,
        backgroundColor = NonggleTheme.colors.g4,
        titleTextStyle = NonggleTheme.typography.b4_btn,
        imageResource = R.drawable.googleimg
    )
}
