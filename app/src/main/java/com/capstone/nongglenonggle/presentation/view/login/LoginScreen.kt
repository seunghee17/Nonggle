package com.capstone.nongglenonggle.presentation.view.login

import android.content.Intent
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
import com.capstone.nongglenonggle.presentation.view.signup.SignupActivity
import kotlinx.coroutines.flow.collectLatest
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onSignInClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect
    val context = LocalContext.current

    LaunchedEffect(true) {
        effectFlow.collectLatest { effect ->
            when (effect) {
                is LoginContract.Effect.NavigateToEnrollUser -> {
                    val intent = Intent(context, SignupActivity::class.java)
                    context.startActivity(intent)
                }
                is LoginContract.Effect.NavigateToHome -> {
                    //context.startActivity(Intent(context, LoginActivity::class.java))
                    //TODO: 임시적으로 회원가입 화면으로 이동
                    val intent = Intent(context, SignupActivity::class.java)
                    context.startActivity(intent)
                }

                is LoginContract.Effect.unAvailableToastmessage -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LaunchedEffect(key1 = uiState.signInState.signInError) {
        uiState.signInState.signInError?.let { error ->
            if(!uiState.signInState.signInError.isNullOrEmpty()) {
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
                viewModel.setEvent(LoginContract.Event.kakaoLoginButtonClick)
            })
            Spacer(modifier = Modifier.height(16.dp))
            googleLoginButton(onClick = {
                onSignInClick()
            })
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
    onClick: () -> Unit
) {
    ImageButton(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        onClick = onClick,
        titleText = "카카오로 시작하기",
        contentColor = NonggleTheme.colors.g1,
        backgroundColor = Color(0xFFF9E000),
        titleTextStyle = NonggleTheme.typography.b4_btn,
        imageResource = R.drawable.kakaobtn
    )
}

@Composable
fun googleLoginButton(
    onClick: () -> Unit
) {
    ImageButton(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        onClick = onClick,
        titleText = "Google로 로그인 하기",
        contentColor = NonggleTheme.colors.g2,
        backgroundColor = NonggleTheme.colors.g4,
        titleTextStyle = NonggleTheme.typography.b4_btn,
        imageResource = R.drawable.googleimg
    )
}
