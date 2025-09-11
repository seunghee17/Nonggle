package com.capstone.nongglenonggle.presentation.view.worker.resume.compose_integration.resume_step3

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.common.button.NonggleIconButton
import com.capstone.nongglenonggle.core.common.textfield.NonggleTextField
import com.capstone.nongglenonggle.core.common.textfield.TextFieldType
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.core.design_system.spoqahanSansneo
import com.capstone.nongglenonggle.presentation.view.worker.resume.compose_integration.WorkerResumeComposeViewModel
import com.capstone.nongglenonggle.presentation.view.worker.resume.compose_integration.WorkerResumeContract

@Composable
fun ResumeStep3Screen(viewModel: WorkerResumeComposeViewModel, ) {

    val uiState by viewModel.select { it.step3 }.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val effectFlow = viewModel.effect
    val focusManager = LocalFocusManager.current

    var isPersonalityFieldFocused by rememberSaveable { mutableStateOf(false) } //자기소개 textfield focus

    LaunchedEffect(Unit) {
        effectFlow.collect { effect ->
            when (effect) {
                else -> {}
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        item {
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = context.getString(R.string.자기소개),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = spoqahanSansneo,
                    fontWeight = FontWeight.Medium
                ),
                color = NonggleTheme.colors.g1
            )
            NonggleTextField(
                modifier = Modifier
                    .padding(bottom = 14.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .onFocusChanged { focusState -> isPersonalityFieldFocused = focusState.isFocused },
                textFieldType = TextFieldType.Standard,
                value = "",
                onValueChange = {

                },
                textStyle = NonggleTheme.typography.b1_main,
                textColor = Color.Black,
                trailingIcon = {
                    if (uiState.introduceDetail.isNotEmpty() && isPersonalityFieldFocused) {
                        NonggleIconButton(
                            ImageResourceId = R.drawable.xcircle,
                            onClick = {
                                viewModel.setEvent(WorkerResumeContract.Event.Step3.ClearIntroduceDetail)
                            }
                        )
                    }
                },

                placeholder = {
                    Text(
                        text = context.getString(R.string.본인을_한문장으로),
                        style = NonggleTheme.typography.b1_main,
                        color = NonggleTheme.colors.g3,
                    )
                },
            )
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = context.getString(R.string.나의성격),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = spoqahanSansneo,
                    fontWeight = FontWeight.Medium
                ),
                color = NonggleTheme.colors.g1
            )
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = context.getString(R.string.추가코멘트),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = spoqahanSansneo,
                    fontWeight = FontWeight.Medium
                ),
                color = NonggleTheme.colors.g1
            )
            additionalInputTextField(
                modifier = Modifier.padding(top = 12.dp),
                context = context,
                additionalValue = "",
                onValueChange = {},
            )
        }
    }
}


//성격 입력
//@Composable
//fun expressMyPersonalityTextField(
//    modifier: Modifier = Modifier,
//    context: Context,
//    introduceDetail: String,
//) {
//    NonggleTextField(
//        modifier = Modifier
//            .padding(bottom = 14.dp)
//            .fillMaxWidth()
//            .wrapContentHeight()
//            .onFocusChanged { focusState -> isPersonalityFieldFocused = focusState.isFocused },
//        textFieldType = TextFieldType.Standard,
//        value = "",
//        onValueChange = {
//
//        },
//        textStyle = NonggleTheme.typography.b1_main,
//        textColor = Color.Black,
//        trailingIcon = {
//            if (introduceDetail.isNotEmpty() && isPersonalityFieldFocused) {
//                NonggleIconButton(
//                    ImageResourceId = R.drawable.xcircle,
//                    onClick = {
//                        viewModel.setEvent(WorkerResumeContract.Event.Step1.ClearUserName)
//                    }
//                )
//            }
//        },
//
//        placeholder = {
//            Text(
//                text = context.getString(R.string.본인을_한문장으로),
//                style = NonggleTheme.typography.b1_main,
//                color = NonggleTheme.colors.g3,
//            )
//        },
//    )
//}

//추가 코멘트입력
@Composable
fun additionalInputTextField(
    modifier: Modifier = Modifier,
    context: Context,
    additionalValue: String,
    onValueChange: (String) -> Unit,
) {
    Row {
        NonggleTextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .onFocusChanged { },
            textFieldType = TextFieldType.Standard,
            value = additionalValue,
            onValueChange = onValueChange,
            textStyle = NonggleTheme.typography.b1_main,
            textColor = Color.Black,
            trailingIcon = {

            },

            placeholder = {
                Text(
                    text = context.getString(R.string.하고싶은_말이나),
                    style = NonggleTheme.typography.b1_main,
                    color = NonggleTheme.colors.g3,
                )
            },
        )
    }
}