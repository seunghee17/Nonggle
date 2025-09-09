package com.capstone.nongglenonggle.presentation.view.worker.resume.compose_integration

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.common.button.ContainedButton
import com.capstone.nongglenonggle.core.common.button.FullButton
import com.capstone.nongglenonggle.core.common.button.NonggleIconButton
import com.capstone.nongglenonggle.core.common.button.OutlinedButton
import com.capstone.nongglenonggle.core.common.date_spinner.DateSpinner
import com.capstone.nongglenonggle.core.common.dialog.NonggleBottomSheet
import com.capstone.nongglenonggle.core.common.textfield.NonggleTextField
import com.capstone.nongglenonggle.core.common.textfield.TextFieldType
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.core.design_system.spoqahanSansneo
import java.time.LocalDate
import java.time.YearMonth
import java.util.Date
import kotlin.math.min

@Composable
fun ResumeStep1Screen(viewModel: WorkerResumeComposeViewModel) {
    val uiState by viewModel.select { it.step1 }.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val effectFlow = viewModel.effect
    val focusManager = LocalFocusManager.current

    var isNameTextFieldFocused by rememberSaveable { mutableStateOf(false) }
    var isCerTificateTextFieldFocused by rememberSaveable { mutableStateOf(false) }
    var showDatePickerSheet by remember { mutableStateOf(false) }

    val pickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let { viewModel.onImagePicked(it) }
    }

    // Photo Picker 미지원 기기 fallback (거의 드물지만 대비)
    val getContentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> uri?.let(viewModel::onImagePicked) }

    //요청할 권한
    val isPhotoPickerAvailable = remember {
        ActivityResultContracts.PickVisualMedia.isPhotoPickerAvailable()
    }

    LaunchedEffect(true) {
        effectFlow.collect { effect ->
            when (effect) {
                is WorkerResumeContract.Effect.Step1.OpenGallery -> {
                    if (isPhotoPickerAvailable) {
                        pickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    } else {
                        // 구형 기기/환경 fallback
                        getContentLauncher.launch("image/*")
                    }
                }
                else -> {}
            }
        }
    }

    if (showDatePickerSheet) {
        dateSpinnerBottomSheet(
            context = context,
            onConfirm = { picked ->
                viewModel.setEvent(WorkerResumeContract.Event.Step1.SetBirthDate(picked))
                showDatePickerSheet = false         // 닫기
            },
            onDismissRequest = { showDatePickerSheet = false },
            initialDate = LocalDate.now().minusYears(20),
            minDate = LocalDate.of(1900, 1, 1),
            maxDate = LocalDate.now()
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .clickable { focusManager.clearFocus() }
    ) {
        item {
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = context.getString(R.string.프로필_이미지),
                style = NonggleTheme.typography.b2_sub,
                color = NonggleTheme.colors.g1
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = context.getString(R.string.본인을_소개할),
                style = NonggleTheme.typography.b2_sub,
                color = NonggleTheme.colors.g2
            )
            if (uiState.imageProfileUri == null) {
                Image(
                    modifier = Modifier
                        .size(width = 96.dp, height = 96.dp)
                        .padding(top = 16.dp)
                        .clickable {
                            viewModel.openGallery()
                        },
                    painter = painterResource(id = R.drawable.imageupload),
                    contentDescription = null,
                )
            } else {
                AsyncImage(
                    model = uiState.imageProfileUri,
                    contentDescription = "",
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = context.getString(R.string.이름),
                style = NonggleTheme.typography.b2_sub,
                color = NonggleTheme.colors.g1
            )
            NonggleTextField(
                modifier = Modifier
                    .padding(bottom = 14.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .onFocusChanged { focusState -> isNameTextFieldFocused = focusState.isFocused },
                textFieldType = TextFieldType.Standard,
                value = uiState.userName,
                onValueChange = {
                    viewModel.setEvent(WorkerResumeContract.Event.Step1.SetUserName(it))
                },
                textStyle = NonggleTheme.typography.b1_main,
                textColor = Color.Black,
                trailingIcon = {
                    if (uiState.userName.isNotEmpty() && isNameTextFieldFocused) {
                        NonggleIconButton(
                            ImageResourceId = R.drawable.xcircle,
                            onClick = {
                                viewModel.setEvent(WorkerResumeContract.Event.Step1.ClearUserName)
                            }
                        )
                    }
                },

                placeholder = {
                    Text(
                        text = context.getString(R.string.본인의_이름을),
                        style = NonggleTheme.typography.b1_main,
                        color = NonggleTheme.colors.g3,
                    )
                },
            )
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = context.getString(R.string.생년월일),
                style = NonggleTheme.typography.b2_sub,
                color = NonggleTheme.colors.g1
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .border(
                        BorderStroke(1.dp, NonggleTheme.colors.g_line),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable {
                        showDatePickerSheet = true
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = uiState.birthDatePresnet,
                        style = NonggleTheme.typography.b4_btn,
                        textAlign = TextAlign.Start
                        //color =  생년월일 유무에 따라 다른 색상 배정
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.date),
                        contentDescription = null,
                    )
                }
            }
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = context.getString(R.string.성별),
                style = NonggleTheme.typography.b2_sub,
                color = NonggleTheme.colors.g1
            )
            Row(
                modifier = Modifier.padding(top = 12.dp)
            ) {
                genderSelectButton(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight()
                        .padding(end = 16.dp),
                    gender = context.getString(R.string.여),
                    selectGender = {
                        viewModel.setEvent(
                            WorkerResumeContract.Event.Step1.SetGenderType(context.getString(
                                    R.string.여
                                ))
                        )
                    },
                    selectedGender = uiState.selectedGender
                )
                genderSelectButton(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight(),
                    gender = context.getString(R.string.남),
                    selectGender = {
                        viewModel.setEvent(
                            WorkerResumeContract.Event.Step1.SetGenderType(context.getString(
                                    R.string.남
                                ))
                        )
                    },
                    selectedGender = uiState.selectedGender
                )
            }
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = context.getString(R.string.자격증),
                style = NonggleTheme.typography.b2_sub,
                color = NonggleTheme.colors.g1
            )
            Row(
                modifier = Modifier.padding(top = 12.dp)
            ) {
                certificationButton(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight()
                        .padding(end = 16.dp),
                    title = context.getString(R.string.있음),
                    changeCertificateState = {
                        viewModel.setEvent(WorkerResumeContract.Event.Step1.SetCertificateAvailable(value = true))
                    },
                    certificateAvailable = uiState.haveCertification ?: false
                )
                certificationButton(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight(),
                    title = context.getString(R.string.없음),
                    changeCertificateState = {
                        viewModel.setEvent(WorkerResumeContract.Event.Step1.SetCertificateAvailable(value = false))
                    },
                    certificateAvailable = uiState.haveCertification ?: false
                )
            }
            if (uiState.haveCertification == true) {
                Row {
                    NonggleTextField(
                        modifier = Modifier
                            .padding(top = 12.dp, end = 16.dp)
                            .weight(1f)
                            .wrapContentHeight()
                            .onFocusChanged { focusState ->
                                isCerTificateTextFieldFocused = focusState.isFocused
                            },
                        textFieldType = TextFieldType.Standard,
                        value = uiState.userCertificateType,
                        onValueChange = {
                            viewModel.setEvent(WorkerResumeContract.Event.Step1.SetUserCertificateDetail(it))
                        },
                        textStyle = NonggleTheme.typography.b1_main,
                        textColor = Color.Black,
                        trailingIcon = {
                            if (uiState.userCertificateType.isNotEmpty() && isCerTificateTextFieldFocused) {
                                NonggleIconButton(
                                    ImageResourceId = R.drawable.xcircle,
                                    onClick = {
                                        viewModel.setEvent(WorkerResumeContract.Event.Step1.ClearUserCertificateDetail)
                                    }
                                )
                            }
                        },
                        placeholder = {
                            Text(
                                text = context.getString(R.string.본인의_이름을),
                                style = NonggleTheme.typography.b1_main,
                                color = NonggleTheme.colors.g3,
                            )
                        },
                    )
                    //자격증 추가 버튼
                    ContainedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        enabled = uiState.userCertificateType.isNotEmpty(),
                        onClick = {
                            viewModel.setEvent(WorkerResumeContract.Event.Step1.AddCertificationChip(uiState.userCertificateType))
                        },
                        titleText = context.getString(R.string.확인),
                        titleTextStyle = TextStyle(
                            fontFamily = spoqahanSansneo,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        ),
                    )
                }
            }

        }
    }

}

@Composable
fun genderSelectButton(
    modifier: Modifier = Modifier,
    gender: String,
    selectGender: () -> Unit,
    selectedGender: String
) {
    OutlinedButton(
        modifier = modifier,
        titleText = gender,
        onClick = selectGender,
        titleTextStyle = TextStyle(
            fontFamily = spoqahanSansneo,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        ),
        enableColor = if (selectedGender == gender) NonggleTheme.colors.m1 else NonggleTheme.colors.g_line,
        enableContentColor = if (selectedGender == gender) NonggleTheme.colors.m1 else NonggleTheme.colors.g3,
        pressedColor = NonggleTheme.colors.m1,
    )
}

@Composable
fun certificationButton(
    modifier: Modifier = Modifier,
    title: String,
    changeCertificateState: () -> Unit,
    certificateAvailable: Boolean
) {
    OutlinedButton(
        modifier = modifier,
        titleText = title,
        onClick = changeCertificateState,
        titleTextStyle = TextStyle(
            fontFamily = spoqahanSansneo,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        ),
        enableColor = if (certificateAvailable && title == "있음") NonggleTheme.colors.m1 else NonggleTheme.colors.g_line,
        enableContentColor = if (certificateAvailable && title == "있음") NonggleTheme.colors.m1 else NonggleTheme.colors.g3,
        pressedColor = NonggleTheme.colors.m1,
    )
}

@Composable
fun certificationResultChip(
    modifier: Modifier = Modifier,
    removeChip: () -> Unit,
    certificationTitle: String,
) {
    OutlinedCard(
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                certificationTitle,
                modifier = Modifier.padding(end = 16.dp),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = spoqahanSansneo,
                    fontWeight = FontWeight.Normal,
                    color = NonggleTheme.colors.g2
                )
            )
            Image(
                modifier = Modifier.padding(end = 16.dp),
                painter = painterResource(id = R.drawable.xcircle),
                contentDescription = null
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dateSpinnerBottomSheet(
    onConfirm: (Date) -> Unit,
    onDismissRequest: () -> Unit,
    context: Context,
    initialDate: LocalDate = LocalDate.now(),
    minDate: LocalDate = LocalDate.of(1900, 1, 1),
    maxDate: LocalDate = LocalDate.of(2100, 12, 31),
) {
    var year by rememberSaveable(initialDate) { mutableStateOf(initialDate.year) }
    var month by rememberSaveable(initialDate) { mutableStateOf(initialDate.monthValue) }
    var day by rememberSaveable(initialDate) { mutableStateOf(initialDate.dayOfMonth) }

    val minYear = minDate.year
    val maxYear = maxDate.year

    // 선택된 연도에 따른 월 범위 동적 제한
    val monthMin = if (year == minYear) minDate.monthValue else 1
    val monthMax = if (year == maxYear) maxDate.monthValue else 12
    month = month.coerceIn(monthMin, monthMax)

    val daysInMonth = YearMonth.of(year, month).lengthOfMonth()
    val dayMin =
        if (year == minYear && month == minDate.monthValue) minDate.dayOfMonth else 1
    val dayMax =
        if (year == maxYear && month == maxDate.monthValue) min(maxDate.dayOfMonth, daysInMonth)
        else daysInMonth
    day = day.coerceIn(dayMin, dayMax)

    NonggleBottomSheet(
        onDismissRequest = onDismissRequest,
        header = {
            Row(
                modifier = Modifier.padding(top = 16.dp, start = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = context.getString(R.string.날짜),
                    style = TextStyle(
                        fontFamily = spoqahanSansneo,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        fontSize = 18.sp
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 20.dp)
                        .clickable { onDismissRequest() },
                    painter = painterResource(id = R.drawable.close),
                    contentDescription = null
                )
            }
        },
        bodyContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .imePadding()
            ) {
                // 스피너는 화면별로 제공하신 DateSpinner 사용
                DateSpinner(
                    year = year,
                    month = month,
                    day = day,
                    onYearChange = { year = it },
                    onMonthChange = { month = it },
                    onDayChange = { day = it },
                    minDate = minDate,
                    maxDate = maxDate
                )

                Spacer(Modifier.height(16.dp))
            }
        },
        footer = {
            FullButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onConfirm(Date(year, month, day))
                    onDismissRequest()
                },
                titleText = context.getString(R.string.확인),
                titleTextStyle = TextStyle(
                    fontFamily = spoqahanSansneo,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.White
                ),
                enabled = true
            )
        }
    )
}