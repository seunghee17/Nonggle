package com.capstone.nongglenonggle.presentation.view.signup

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.common.appbar.NonggleAppBar
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.core.noRippleClickable
import com.capstone.nongglenonggle.presentation.view.worker.home.WorkerMainActivity


@Composable
fun SignupAgreeTermsScreen (
    navController: NavHostController,
    viewModel: SignupViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect
    val context = LocalContext.current

    val scrollState = rememberScrollState()

    LaunchedEffect(true) {
        effectFlow.collect { effect ->
            when (effect) {
                is SignupContract.Effect.NavigateToStep3Screen -> {
                    navController.navigate("signup/step3")
                }
                is SignupContract.Effect.NavigateToHomeScreen -> { //구직자회원의 홈화면으로
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
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        NonggleAppBar(
            onBackPressed = { navController.popBackStack() },
            backAction = true,
            title = {},
        )
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = context.getString(R.string.sign_up_Title),
            style = NonggleTheme.typography.TextInputEditTextStyle,
            color = NonggleTheme.colors.g2,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = context.getString(R.string.sign_up_agree_rules_subTitle),
                fontWeight = FontWeight.W400,
                fontSize = 24.sp,
                color = Color(0xFF1E1E1E),
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        termsAllCheckBoxButton(
            modifier = Modifier.padding(horizontal = 20.dp),
            onClick = {
                viewModel.setEvent(SignupContract.Event.AcitivateAllTermCheckBox)
            },
            termInfoText = context.getString(R.string.sign_up_agree_content_allAgree),
            checkBoxState = uiState.allCheckBoxState
        )
        termsCheckBoxButton(
            modifier = Modifier.padding(horizontal = 20.dp),
            onClick = {
                viewModel.setEvent(SignupContract.Event.AcitivateAgeLimitCheckBox)
            },
            termInfoText = context.getString(R.string.sign_up_agree_content_ageLimit),
            checkBoxState = uiState.ageLimitConfirmCheckBox
        )

        termsCheckBoxButton(
            modifier = Modifier.padding(horizontal = 20.dp),
            onClick = {
                viewModel.setEvent(SignupContract.Event.AcitivateServiceUseTermCheckBox)
            },
            termInfoText = context.getString(R.string.sign_up_agree_content_serviceAgree),
            checkBoxState = uiState.serviceUseTermCheckBox
        )
        termsCheckBoxButton(
            modifier = Modifier.padding(horizontal = 20.dp),
            onClick = {
                viewModel.setEvent(SignupContract.Event.AcitivatePersonalInfoCheckBox)
            },
            termInfoText = context.getString(R.string.sign_up_agree_content_personalCollectInfoAgree),
            checkBoxState = uiState.personalInfoCheckBox
        )
        Spacer(modifier = Modifier.weight(1f))
        nextBtn(
            context = context,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            enable = true,
            onClick = {
                if(uiState.userSignupType == UserType.MANAGER) {
                    viewModel.setEvent(event = SignupContract.Event.navigateToStep3Button)
                } else {
                    viewModel.setEvent(event = SignupContract.Event.navigateToHomeButton)
                }
            })
    }
}

@Composable
fun termsCheckBoxButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    termInfoText: String,
    checkBoxState: Boolean,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .noRippleClickable {
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (checkBoxState) {
                Icon(
                    painter = painterResource(id = R.drawable.checkedcircle),
                    modifier = Modifier.size(width = 24.dp, height = 24.dp),
                    contentDescription = null,
                    tint = NonggleTheme.colors.m1
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.checkcircle),
                    modifier = Modifier.size(width = 24.dp, height = 24.dp),
                    contentDescription = null,
                    tint = NonggleTheme.colors.g3
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = termInfoText,
                style = NonggleTheme.typography.TextInputEditTextStyle,
                color = NonggleTheme.colors.g2,
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.caretright),
                modifier = Modifier.size(width = 20.dp, height = 20.dp),
                contentDescription = null,
            )
        }
    }
}

@Composable
fun termsAllCheckBoxButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    termInfoText: String,
    checkBoxState: Boolean,
) {
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .noRippleClickable {
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = NonggleTheme.colors.g_line
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (checkBoxState) {
                Icon(
                    painter = painterResource(id = R.drawable.checkedcircle),
                    modifier = Modifier.size(width = 24.dp, height = 24.dp),
                    contentDescription = null,
                    tint = NonggleTheme.colors.m1
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.checkcircle),
                    modifier = Modifier.size(width = 24.dp, height = 24.dp),
                    contentDescription = null,
                    tint = NonggleTheme.colors.g3
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = termInfoText,
                style = NonggleTheme.typography.TextInputEditTextStyle,
                color = NonggleTheme.colors.g2,
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.caretright),
                modifier = Modifier.size(width = 20.dp, height = 20.dp),
                contentDescription = null,
            )
        }
    }
}

