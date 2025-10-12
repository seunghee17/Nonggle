package com.capstone.nongglenonggle.presentation.view.login.component

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.common.button.ImageButton
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.core.design_system.soYo

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
fun KakaoLoginButton(
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
fun GoogleLoginButton(
    onClick: () -> Unit,
    context: Context
) {
    ImageButton(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        onClick = onClick,
        titleText = context.getString(R.string.start_with_google),
        contentColor = NonggleTheme.colors.g2,
        backgroundColor = NonggleTheme.colors.g4,
        titleTextStyle = NonggleTheme.typography.b4_btn,
        imageResource = R.drawable.googleimg
    )
}
