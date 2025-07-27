package com.capstone.nongglenonggle.presentation.view.onboarding

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.common.button.ImageButton
import com.capstone.nongglenonggle.core.common.button.OutlinedButton
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.core.design_system.NongleTheme
import com.capstone.nongglenonggle.core.design_system.soYo
import com.capstone.nongglenonggle.presentation.view.signup.SignupActivity
import kotlinx.coroutines.flow.collectLatest
import androidx.hilt.navigation.compose.hiltViewModel
import com.capstone.nongglenonggle.presentation.view.login.LoginActivity

@Composable
fun StartNonggleScreen() {
    val viewModel: StartNonggleViewModel = hiltViewModel()
    val effectFlow = viewModel.effect
    val context = LocalContext.current

    LaunchedEffect(true) {
        effectFlow.collectLatest { effect ->
            when (effect) {
                is StartNonggleContract.Effect.NavigateToSignUp -> {
                    context.startActivity(Intent(context, SignupActivity::class.java))
                }
                is StartNonggleContract.Effect.NavigateToLogin -> {
                    context.startActivity(Intent(context, LoginActivity::class.java))
                }

                is StartNonggleContract.Effect.unAvailableToastmessage -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
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
                viewModel.setEvent(
                    StartNonggleContract.Event.kakaoLoginButtonClickEvent(
                        context,
                    )
                )
            })
            Spacer(modifier = Modifier.height(16.dp))
            googleLoginButton(onClick = {
                viewModel.setEvent(
                    StartNonggleContract.Event.googleLoginButtonClickEvent(
                        context,
                    )
                )
            })
            Spacer(modifier = Modifier.height(20.dp))
            divideLine()
            Spacer(modifier = Modifier.height(20.dp))
            signUpWithPhoneNumber(onClick = {
                viewModel.setEvent(
                    StartNonggleContract.Event.signUpWithPhoneNumberButtonClickEvent(context)
                )
            })
            Spacer(modifier = Modifier.height(16.dp))
            loginButtonText(onClick = {
                viewModel.setEvent(
                    StartNonggleContract.Event.loginButtonClickEvent(context)
                )
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
        titleText = "Google로 시작하기",
        contentColor = NonggleTheme.colors.g2,
        backgroundColor = NonggleTheme.colors.g4,
        titleTextStyle = NonggleTheme.typography.b4_btn,
        imageResource = R.drawable.googleimg
    )
}

@Composable
fun divideLine() {
    val lineColor = NonggleTheme.colors.g_line
    Row(
        modifier = Modifier.padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Canvas(
            modifier = Modifier.weight(1f),
            onDraw = {
                drawLine(
                    color = lineColor,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx()
                )
            })
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = "OR",
            color = NonggleTheme.colors.g2,
            style = NonggleTheme.typography.b2_sub,
        )
        Canvas(
            modifier = Modifier.weight(1f),
            onDraw = {
                drawLine(
                    color = lineColor,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx()
                )
            })
    }
}

@Composable
fun signUpWithPhoneNumber(
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        onClick = onClick,
        enabled = true,
        enableColor = NonggleTheme.colors.g_line,
        pressedColor = NonggleTheme.colors.m2,
        titleText = "전화번호로 가입하기",
        titleTextStyle = NonggleTheme.typography.b4_btn,
    )
}

@Composable
fun loginButtonText(
    onClick: () -> Unit
) {
    Text(
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        text = "로그인",
        color = NonggleTheme.colors.g2,
        style = NonggleTheme.typography.b2_sub,
    )
}

@Preview
@Composable
fun PreviewWidget() {
    divideLine()
}
