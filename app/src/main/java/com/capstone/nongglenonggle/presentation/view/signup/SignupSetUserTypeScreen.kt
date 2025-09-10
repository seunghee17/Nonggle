package com.capstone.nongglenonggle.presentation.view.signup

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.app.Screens
import com.capstone.nongglenonggle.core.common.button.FullButton
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.core.design_system.spoqahanSansneo
import com.capstone.nongglenonggle.core.noRippleClickable
import kotlinx.coroutines.flow.collectLatest


@Composable
fun SetUserTypeScreen(
    navController: NavHostController,
    viewModel: SignupViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect
    val context = LocalContext.current

    LaunchedEffect(true) {
        effectFlow.collectLatest { effect ->
            when(effect) {
                is SignupContract.Effect.NavigateToStep1Screen -> {
                    navController.navigate(Screens.Signup.Step2.route)
                }
                is SignupContract.Effect.SetToastMessage -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    // Handle other effects
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        SelectTypeDescription()
        Spacer(modifier = Modifier.height(10.dp))
        userTypeContainer(
            UserType.MANAGER,
            modifier = Modifier.padding(horizontal = 20.dp),
            onClick = {
                viewModel.setEvent(SignupContract.Event.SelectUseTypeBox(UserType.MANAGER))
            },
            selectType = uiState.userSignupType
        )
        Spacer(modifier = Modifier.height(16.dp))
        userTypeContainer(
            UserType.WORKER,
            modifier = Modifier.padding(horizontal = 20.dp),
            onClick = {
                viewModel.setEvent(SignupContract.Event.SelectUseTypeBox(UserType.WORKER))
            },
            selectType = uiState.userSignupType
        )
        Spacer(modifier = Modifier.weight(1f))
        nextBtn(
            context = context,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            enable = true,
            onClick = {
                viewModel.setEvent(SignupContract.Event.navigateToStep1Button)
            })
    }
}

@Composable
fun SelectTypeDescription() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(top = 10.dp),
            text = "가입 유형",
            color = NonggleTheme.colors.g2,
            style = NonggleTheme.typography.b1_main,
        )
        Text(
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(top = 10.dp),
            text = "가입할 유형을 선택해주세요.",
            color = Color.Black,
            style = NonggleTheme.typography.t1,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun userTypeContainer(
    type: UserType,
    modifier: Modifier,
    onClick: () -> Unit,
    selectType: UserType
) {
    val titleText = if (type == UserType.WORKER) "구직자 회원" else "구인자 회원"
    val descriptionText = if (type == UserType.WORKER) "일자리를 찾고싶어요!" else "일손을 찾고싶어요!"
    val imageResourceId =
        if (type == UserType.WORKER) R.drawable.job_seeker_png else R.drawable.img_offer_circle

    CompositionLocalProvider(LocalRippleConfiguration provides null) {
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
                1.dp,
                if (type == selectType) NonggleTheme.colors.m1 else NonggleTheme.colors.g_line
            ),
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.Top,
                ) {
                    Text(
                        text = titleText,
                        style = NonggleTheme.typography.t3,
                        color = Color.Black
                    )
                    Text(
                        modifier = Modifier.padding(top = 12.dp),
                        text = descriptionText,
                        style = TextStyle(
                            fontFamily = spoqahanSansneo,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            letterSpacing = 0.sp,
                            platformStyle = PlatformTextStyle(includeFontPadding = false),
                        ),
                        color = NonggleTheme.colors.g2
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Column() {
                    Spacer(modifier = Modifier.height(20.dp))
                    Image(
                        modifier = Modifier
                            .size(width = 80.dp, height = 80.dp),
                        painter = painterResource(id = imageResourceId),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Composable
fun nextBtn(context: Context, modifier: Modifier, enable: Boolean, onClick: () -> Unit) {
    FullButton(
        modifier = modifier,
        enabled = enable,
        onClick = onClick,
        titleText = context.getString(R.string.next_btn_Title),
        titleTextStyle = NonggleTheme.typography.t3
    )
}