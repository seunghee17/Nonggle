package com.capstone.nongglenonggle.presentation.view.signup

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.capstone.nongglenonggle.app.Screens
import com.capstone.nongglenonggle.core.common.appbar.NonggleAppBar
import com.capstone.nongglenonggle.core.common.button.ContainedButton
import com.capstone.nongglenonggle.core.common.button.NonggleIconButton
import com.capstone.nongglenonggle.core.common.button.stepButton
import com.capstone.nongglenonggle.core.common.textfield.NonggleTextField
import com.capstone.nongglenonggle.core.common.textfield.TextFieldType

@Composable
fun SignupStep1Screen(
    navController: NavHostController,
    viewModel: SignupComposeViewModel,
    ) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect
    val context = LocalContext.current

    val scrollState = rememberScrollState()

    LaunchedEffect(true) {
        effectFlow.collect { effect ->
            when (effect) {
                is SignupContract.Effect.NavigateToStep2Screen -> {
                    navController.navigate(Screens.Signup.Step2.route)
                }

                is SignupContract.Effect.setToastMessage -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                else -> {

                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),

    ) {
        NonggleAppBar(
            onBackPressed = { },
            title = {},
        )
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            textAlign = TextAlign.Start,
            text = "회원가입",
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
                text = "개인정보",
                fontWeight = FontWeight.W400,
                fontSize = 24.sp,
                color = Color(0xFF1E1E1E),
            )
            Spacer(modifier = Modifier.weight(1f))
            stepButton("1", SignupStep.STEP1.stepNum.toString())
            Spacer(modifier = Modifier.width(10.dp))
            stepButton("2", SignupStep.STEP1.stepNum.toString())
            if (uiState.userSignupType == UserType.MANAGER) {
                Spacer(modifier = Modifier.width(10.dp))
                stepButton("3", SignupStep.STEP1.stepNum.toString())
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        /// 이름 작성 TextField
        userNameInputTextField(
            modifier = Modifier.padding(horizontal = 10.dp),
            value = uiState.userName,
            onValueChange = {
                viewModel.setEvent(SignupContract.Event.UserInsertName(it))
            },
            clearValueAction = {
                viewModel.setEvent(SignupContract.Event.ClearUserName)
            },
        )
        Spacer(modifier = Modifier.height(14.dp))
        /// 핸드폰 번호 TextField
        userPhoneNumberInputTextField(
            modifier = Modifier.padding(horizontal = 20.dp),
            value = uiState.phoneNumber,
            onValueChange = {
                viewModel.setEvent(SignupContract.Event.UserInsertPhoneNumber(it))
            },
            clearValueAction = {
                viewModel.setEvent(SignupContract.Event.ClearUserPhoneNumber)
            },
            supportText = {
                if(uiState.phoneNumber.contains("-")) {
                    Text(
                        text = "기호 없이 번호만 입력해주세요.",
                        style = NonggleTheme.typography.HintTextAppearance.copy(fontSize = 12.sp),
                        color = NonggleTheme.colors.error,
                    )
                }
            },
            sendVerificationCode = {
                viewModel.setEvent(SignupContract.Event.sendVerificationCode(uiState.phoneNumber))
            }
        )
        Spacer(modifier = Modifier.height(14.dp))
        /// 인증번호 TextField
        verifyNumberTextField(
            modifier = Modifier.padding(horizontal = 20.dp),
            value = uiState.verificationCode,
            onValueChange = {
                viewModel.setEvent(SignupContract.Event.UserInsertVerificationCode(it))
            },
            clearValueAction = {
                viewModel.setEvent(SignupContract.Event.ClearUserVerificationCode)
            },
            supportText = {

            },
            checkVerificationCode = {
                viewModel.setEvent(SignupContract.Event.VerificationCodeCheck(uiState.verificationCode))
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        nextBtn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            enable = uiState.authverificationState,
            onClick = {
                viewModel.setEffect(SignupContract.Effect.NavigateToStep2Screen)
            })
    }
}

@Composable
fun userNameInputTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    clearValueAction: () -> Unit,
) {
    var isTextFieldFocused by remember { mutableStateOf(false) }
    NonggleTextField(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .onFocusChanged { focusState -> isTextFieldFocused = focusState.isFocused },
        textFieldType = TextFieldType.Standard,
        value = value,
        onValueChange = onValueChange,
        textStyle = NonggleTheme.typography.TextInputEditTextStyle,
        textColor = Color(0xFF1E1E1E),
        trailingIcon = {
            if (value.isNotEmpty() && isTextFieldFocused) {
                NonggleIconButton(
                    ImageResourceId = R.drawable.xcircle,
                    onClick = clearValueAction
                )
            }
        },
        placeholder = {
            Text(
                text = "본인의 이름을 입력해주세요.",
                style = NonggleTheme.typography.HintTextAppearance,
                color = NonggleTheme.colors.g3,
            )
        },
        label = {
            Text(
                modifier = modifier,
                text = "본인 이름",
                style = NonggleTheme.typography.b2_sub,
                color = NonggleTheme.colors.g1,
            )
        }
    )
}

@Composable
fun userPhoneNumberInputTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    clearValueAction: () -> Unit,
    supportText: @Composable (() -> Unit),
    sendVerificationCode: () -> Unit,
) {
    var isTextFieldFocused by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .onFocusChanged { focusState -> isTextFieldFocused = focusState.isFocused },
            verticalAlignment = Alignment.CenterVertically
        ) {
            NonggleTextField(
                modifier = Modifier
                    .weight(1f)
                    .onFocusChanged { focusState -> isTextFieldFocused = focusState.isFocused },
                textFieldType = TextFieldType.Standard,
                value = value,
                onValueChange = onValueChange,
                textStyle = NonggleTheme.typography.TextInputEditTextStyle,
                textColor = Color(0xFF1E1E1E),
                trailingIcon = {
                    if (value.isNotEmpty() && isTextFieldFocused) {
                        NonggleIconButton(
                            ImageResourceId = R.drawable.xcircle,
                            onClick = clearValueAction
                        )
                    }
                },
                placeholder = {
                    Text(
                        text = "전화번호를 입력해주세요",
                        style = NonggleTheme.typography.HintTextAppearance,
                        color = NonggleTheme.colors.g3,
                    )
                },
                supportText = supportText,
                label = {
                    Text(
                        text = "아이디",
                        style = NonggleTheme.typography.b2_sub,
                        color = NonggleTheme.colors.g1,
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
            Spacer(modifier = Modifier.width(16.dp))
            ContainedButton(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(IntrinsicSize.Min),
                enabled = value.isNotEmpty(),
                onClick = {
                    sendVerificationCode()
                },
                titleText = "인증번호",
                titleTextStyle = NonggleTheme.typography.b1_main,
            )
        }
    }
}

@Composable
fun verifyNumberTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    clearValueAction: () -> Unit,
    supportText: @Composable (() -> Unit),
    checkVerificationCode: () -> Unit,
) {
    var isTextFieldFocused by remember { mutableStateOf(false) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        NonggleTextField(
            modifier = Modifier
                .weight(1f)
                .onFocusChanged { focusState -> isTextFieldFocused = focusState.isFocused },
            textFieldType = TextFieldType.Standard,
            value = value,
            onValueChange = onValueChange,
            textStyle = NonggleTheme.typography.TextInputEditTextStyle,
            textColor = Color(0xFF1E1E1E),
            trailingIcon = {
                if (value.isNotEmpty() && isTextFieldFocused) {
                    NonggleIconButton(
                        ImageResourceId = R.drawable.xcircle,
                        onClick = clearValueAction
                    )
                }
            },
            placeholder = {
                Text(
                    text = "예) 1234",
                    style = NonggleTheme.typography.HintTextAppearance,
                    color = NonggleTheme.colors.g3,
                )
            },
            supportText = supportText,
            label = {
                Text(
                    text = "인증번호",
                    style = NonggleTheme.typography.b2_sub,
                    color = NonggleTheme.colors.g1,
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        Spacer(modifier = Modifier.width(16.dp))
        ContainedButton(
            modifier = Modifier
                .wrapContentWidth()
                .height(IntrinsicSize.Min),
            enabled = value.isNotEmpty() && isTextFieldFocused,
            onClick = {
                checkVerificationCode()
            },
            titleText = "확인",
            titleTextStyle = NonggleTheme.typography.b1_main,
        )
    }
}

