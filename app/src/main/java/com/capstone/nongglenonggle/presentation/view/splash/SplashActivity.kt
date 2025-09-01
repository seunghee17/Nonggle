package com.capstone.nongglenonggle.presentation.view.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.core.design_system.soYo
import com.capstone.nongglenonggle.presentation.view.worker.home.WorkerMainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel = hiltViewModel<SplashViewModel>()
            SpalashScreen(viewModel = viewModel)
        }

    }

}

@Composable
fun SpalashScreen(
    viewModel: SplashViewModel,
) {
    val effectFlow = viewModel.effect
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        delay(2000L)
        viewModel.getUserLoginType()
    }

    LaunchedEffect(true) {
        effectFlow.collectLatest { effect ->
            when (effect) {

                is SplashContract.Effect.NavigateToFarmerHome -> {
//                    val intent = Intent(context, MainActivity::class.java).apply {
//                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                    }
//                    context.startActivity(intent)
                    val intent = Intent(context, WorkerMainActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    }
                    context.startActivity(intent)
                }

                is SplashContract.Effect.NavigateToWorkerHome -> {
                    val intent = Intent(context, WorkerMainActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    }
                    context.startActivity(intent)
                }

                else -> {}
            }
        }
    }

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

@Preview
@Composable
fun PreviewSplashScreen() {
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
            "농글",
            fontSize = 24.sp,
            fontFamily = soYo,
            fontWeight = FontWeight.Bold,
            color = NonggleTheme.colors.m1
        )
        Spacer(modifier = Modifier.weight(weight = 1f))
        Text(
            "함께_가꾸어가는_씨앗에서_웃음을_맺기까지",
            modifier = Modifier
                .padding(bottom = 60.dp),
            fontSize = 14.sp,
            fontFamily = soYo,
            fontWeight = FontWeight.Normal,
            color = NonggleTheme.colors.m1
        )
    }
}