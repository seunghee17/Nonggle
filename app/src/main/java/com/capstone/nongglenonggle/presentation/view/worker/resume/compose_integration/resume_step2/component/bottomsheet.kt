package com.capstone.nongglenonggle.presentation.view.worker.resume.compose_integration.resume_step2.component

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.common.button.FullButton
import com.capstone.nongglenonggle.core.common.dialog.NonggleBottomSheet
import com.capstone.nongglenonggle.core.common.textfield.NonggleTextField
import com.capstone.nongglenonggle.core.common.textfield.TextFieldType
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.core.design_system.spoqahanSansneo
import com.capstone.nongglenonggle.core.noRippleClickable
import com.capstone.nongglenonggle.presentation.view.worker.resume.component.ExposedDropMenuStateHolder
import com.capstone.nongglenonggle.presentation.view.worker.resume.component.rememberExposedMenuStateHolder
import com.capstone.nongglenonggle.presentation.view.worker.resume.compose_integration.WorkerResumeComposeViewModel
import com.capstone.nongglenonggle.presentation.view.worker.resume.compose_integration.WorkerResumeContract


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumeCareerAddBottomSheet(
    modifier: Modifier = Modifier,
    viewModel: WorkerResumeComposeViewModel,
    context: Context,
    onDismissRequest: () -> Unit,
) {
    val uiState by viewModel.select { it.step2 }.collectAsStateWithLifecycle()

    var showCalendarDialog by remember { mutableStateOf(false) }

    if(showCalendarDialog) {

    }

    NonggleBottomSheet(
        onDismissRequest = onDismissRequest,
        header = {
            Row(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
            ) {
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    modifier = modifier.padding(top = 20.dp, end = 20.dp),
                    onClick = onDismissRequest,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.close),
                        modifier = modifier.size(width = 24.dp, height = 24.dp),
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }
        },
        bodyContent = {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxSize()
            ) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        context.getString(R.string.경력추가하기),
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = spoqahanSansneo,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(
                        context.getString(R.string.경력소개),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = spoqahanSansneo,
                            fontWeight = FontWeight.Medium,
                            color = NonggleTheme.colors.g1
                        )
                    )
                    NonggleTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 4.dp),
                        textFieldType = TextFieldType.Standard,
                        value = uiState.careerTextFieldValue,
                        onValueChange = {
                            viewModel.setEvent(WorkerResumeContract.Event.Step2.SetCareerTitle(it))
                        },
                        placeholder = {
                            Text(
                                text = context.getString(R.string.경력을_소개해),
                                style = NonggleTheme.typography.b1_main
                            )
                        },
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        context.getString(R.string.작업기간),
                        modifier = Modifier.padding(bottom = 12.dp),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = spoqahanSansneo,
                            fontWeight = FontWeight.Medium,
                            color = NonggleTheme.colors.g1
                        )
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        periodOfWork(
                            modifier = Modifier
                                .weight(0.5f)
                                .wrapContentHeight(),
                            onClick = {
                                viewModel.setEvent(
                                    WorkerResumeContract.Event.Step2.SetWorkPeriodRange(false)
                                )
                            },
                            title = context.getString(R.string.개월_미만),
                            isActivate = uiState.careerAddBottomSheetState.isLongerThenMonth == false,
                        )
                        periodOfWork(
                            modifier = Modifier
                                .weight(0.5f)
                                .wrapContentHeight(),
                            onClick = {
                                viewModel.setEvent(
                                    WorkerResumeContract.Event.Step2.SetWorkPeriodRange(true)
                                )
                            },
                            title = context.getString(R.string.개월_이상),
                            isActivate = uiState.careerAddBottomSheetState.isLongerThenMonth == true
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    if(uiState.careerAddBottomSheetState.isLongerThenMonth != null) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            selectCalendarBox(
                                modifier = Modifier
                                    .weight(0.5f)
                                    .wrapContentHeight(),
                                onClick = {showCalendarDialog = true},
                                title = if (uiState.careerAddBottomSheetState.careerStartDate == null) context.getString(
                                    R.string.근무시작일) else (uiState.careerAddBottomSheetState.showCareerStartDate ?: ""),
                                titleColor = if(uiState.careerAddBottomSheetState.careerStartDate == null) NonggleTheme.colors.g3 else Color.Black,
                            )
                            if(uiState.careerAddBottomSheetState.isLongerThenMonth == true) {
                                selectCalendarBox(
                                    modifier = Modifier
                                        .weight(0.5f)
                                        .wrapContentHeight(),
                                    onClick = {showCalendarDialog = true}, //FIXME: state 값 업데이트는 아직 안이뤄짐
                                    title = if (uiState.careerAddBottomSheetState.careerEndDate == null) context.getString(
                                        R.string.근무시작일) else (uiState.careerAddBottomSheetState.showCareerEndDate ?: ""),
                                    titleColor = if(uiState.careerAddBottomSheetState.careerEndDate == null) NonggleTheme.colors.g3 else Color.Black,
                                )
                            } else if(uiState.careerAddBottomSheetState.isLongerThenMonth == false) {
                                selectDateBox(
                                    modifier = Modifier
                                        .weight(0.5f),
                                    onClick = {},
                                    title = if (uiState.careerAddBottomSheetState.careerPeriodDay == null) context.getString(
                                        R.string.근무_일) else (uiState.careerAddBottomSheetState.showCareerEndDate ?: ""),
                                    titleColor = if(uiState.careerAddBottomSheetState.careerPeriodDay == null) NonggleTheme.colors.g3 else Color.Black,
                                    stateHolder = rememberExposedMenuStateHolder()
                                )
                            }
                        }
                    }
                    Text(
                        context.getString(R.string.작업내용),
                        modifier = Modifier.padding(top = 32.dp),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = spoqahanSansneo,
                            fontWeight = FontWeight.Medium,
                            color = NonggleTheme.colors.g1
                        )
                    )
                    NonggleTextField(
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .fillMaxWidth()
                            .height(144.dp),
                        containerColor = Color.White,
                        textFieldType = TextFieldType.Filled,
                        value = uiState.careerAddBottomSheetState.careerDetailContent,
                        onValueChange = {
                            viewModel.setEvent(event = WorkerResumeContract.Event.Step2.SetCareerDetail(
                                it
                            )
                            )
                        },
                        placeholder = {
                            Text(text = context.getString(R.string.업무내용을_상세히), style = NonggleTheme.typography.b1_main.copy(color = NonggleTheme.colors.g3))
                        },
                    )
                }
            }
        },
        footer = {
            FullButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                enabled = true,
                onClick = {}, //FIXME: 경력 추가 하는 기능 구현하기
                titleText = context.getString(R.string.추가하기),
                titleTextStyle = NonggleTheme.typography.t3
            )
        }
    )
}

@Composable
fun periodOfWork(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    title: String,
    isActivate: Boolean,
) {
    OutlinedCard(
        modifier = modifier
            .wrapContentHeight()
            .noRippleClickable {
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (isActivate) NonggleTheme.colors.m1 else NonggleTheme.colors.g_line
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = title,
                style = NonggleTheme.typography.b1_main,
                color = if (isActivate) NonggleTheme.colors.m1 else NonggleTheme.colors.g3,
            )
        }
    }
}

@Composable
fun selectCalendarBox (
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    title: String,
    titleColor: Color,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                BorderStroke(1.dp, NonggleTheme.colors.g_line),
                shape = RoundedCornerShape(4.dp)
            )
            .noRippleClickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = NonggleTheme.typography.b4_btn,
                textAlign = TextAlign.Start,
                color =  titleColor
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.date),
                contentDescription = null,
            )
        }
    }
}

@Composable
fun selectDateBox (
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    title: String,
    titleColor: Color,
    stateHolder: ExposedDropMenuStateHolder
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                BorderStroke(1.dp, NonggleTheme.colors.g_line),
                shape = RoundedCornerShape(4.dp)
            )
            .noRippleClickable {
                onClick()
            }
            .onGloballyPositioned { stateHolder.onSize(it.size.toSize()) },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = NonggleTheme.typography.b4_btn,
                textAlign = TextAlign.Start,
                color =  titleColor
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.date),
                contentDescription = null,
            )
        }
    }
    DropdownMenu(
        modifier = Modifier.width(with(LocalDensity.current) {stateHolder.size.width.toDp()}),
        expanded = stateHolder.enabled,
        onDismissRequest = {
            stateHolder.onEabled(false)
        }
    ) {
        stateHolder.items.forEachIndexed { index, s ->
            DropdownMenuItem(
                text = { Text(text = s) },
                onClick = {
                    stateHolder.onSelectedIndex(index)
                    stateHolder.onEabled(false)
                }
            )
        }
    }
}