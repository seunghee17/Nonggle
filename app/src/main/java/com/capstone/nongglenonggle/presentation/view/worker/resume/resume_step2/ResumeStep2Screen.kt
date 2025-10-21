package com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step2

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.core.design_system.spoqahanSansneo
import com.capstone.nongglenonggle.core.noRippleClickable
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step2.component.ResumeCareerAddBottomSheet
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step2.component.careerItem
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step2.ResumeStep2Contract.Event as Step2Event
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step2.ResumeStep2Contract.Effect as Step2Effect
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step2.ResumeStep2Contract.State as Step2State


@Composable
internal fun ResumeStep2Route(
    viewModel: ResumeStep2ViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect
    val context = LocalContext.current

    LaunchedEffect(true) {
        effectFlow.collect { effect ->
            when (effect) {
                is Step2Effect.ShowErrorToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    ResumeStep2Screen(
        state = uiState,
        onEvent = viewModel::setEvent
    )
}

@Composable
fun ResumeStep2Screen(
    state: Step2State,
    onEvent: (Step2Event) -> Unit,
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current


    if (state.showCareerAddBottomSheet) {
        ResumeCareerAddBottomSheet(
            state = state,
            onEvent = onEvent,
            context = context,
            onDismissRequest = { onEvent(Step2Event.ShowCareerBottomSheet(false)) }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .clickable(
                onClick = { focusManager.clearFocus() }
            )
    ) {
        item {
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = context.getString(R.string.경력사항),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = spoqahanSansneo,
                    fontWeight = FontWeight.Medium
                ),
                color = NonggleTheme.colors.g1
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = context.getString(R.string.어플_사용_이전),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = spoqahanSansneo,
                    fontWeight = FontWeight.Normal
                ),
                color = NonggleTheme.colors.g2
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 16.dp, bottom = 16.dp)
                    .border(
                        BorderStroke(1.dp, NonggleTheme.colors.m1),
                        shape = RoundedCornerShape(4.dp)
                    ),

                ) {
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        modifier = Modifier.padding(vertical = 16.dp),
                        textAlign = TextAlign.Center,
                        text = state.totalPeriodParsing,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = spoqahanSansneo,
                            fontWeight = FontWeight.Medium,
                            color = NonggleTheme.colors.m1
                        )
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
        this.items(
            items = state.careerList,
            key = { it.id }
        ) { item ->
            careerItem(item, {
                onEvent(Step2Event.RemoveCareerItem(item = item))
            })
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 32.dp)
                    .border(
                        BorderStroke(1.dp, NonggleTheme.colors.g_line),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .noRippleClickable {
                        onEvent(Step2Event.ShowCareerBottomSheet(true))
                    },
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = context.getString(R.string.경력추가하기),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = spoqahanSansneo,
                            fontWeight = FontWeight.Medium,
                            color = NonggleTheme.colors.g3
                        )
                    )
                    Spacer(modifier = Modifier.weight(weight = 1f))
                    Image(
                        modifier = Modifier.size(width = 24.dp, height = 24.dp),
                        painter = painterResource(R.drawable.right_small),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResumeStep2PreviewScreen() {
    ResumeStep2Screen(
        state = Step2State(),
        onEvent = {}
    )
}