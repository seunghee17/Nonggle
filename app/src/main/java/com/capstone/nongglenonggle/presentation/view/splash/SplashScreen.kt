package com.capstone.nongglenonggle.presentation.view.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.core.design_system.soYo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import com.capstone.nongglenonggle.presentation.view.splash.SplashContract.Effect as SplashEffect

@Composable
internal fun SplashRoute(
    viewModel: SplashViewModel,
    navigateToWorkerHome:() -> Unit,
    navigateToLogin: () -> Unit,
) {
    val effectFlow = viewModel.effect

    LaunchedEffect(Unit) {
        delay(2000L)
    }

    LaunchedEffect(true) {
        effectFlow.collectLatest { effect ->
            when (effect) {
                is SplashEffect.NavigateToWorkerHome -> navigateToWorkerHome()
                is SplashEffect.NavigateToLogin -> navigateToLogin()
            }
        }
    }
}

@Composable
fun SpalashScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(weight = 1f))
        Image(
            modifier = Modifier.size(width = 130.dp, height = 130.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
        )
        Text(
            context.getString(R.string.농글),
            fontSize = 24.sp,
            fontFamily = soYo,
            fontWeight = FontWeight.Bold,
            color = NonggleTheme.colors.m1
        )
        Spacer(modifier = Modifier.weight(weight = 1f))
        Text(
            context.getString(R.string.함께_가꾸어가는_씨앗에서_웃음을_맺기까지),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 60.dp),
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            fontFamily = soYo,
            fontWeight = FontWeight.Normal,
            color = NonggleTheme.colors.m1
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewSplashScreen() {
    NonggleTheme {
        SpalashScreen()
    }
}