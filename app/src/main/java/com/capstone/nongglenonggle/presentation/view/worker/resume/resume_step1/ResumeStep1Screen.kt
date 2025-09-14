package com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step1

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.common.button.ContainedButton
import com.capstone.nongglenonggle.core.common.button.NonggleIconButton
import com.capstone.nongglenonggle.core.common.textfield.NonggleTextField
import com.capstone.nongglenonggle.core.common.textfield.TextFieldType
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.core.design_system.spoqahanSansneo
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step1.component.certificationButton
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step1.component.certificationChipItem
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step1.component.dateSpinnerBottomSheet
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step1.component.genderSelectButton
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step1.ResumeStep1Contract.Effect as Step1Effect
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step1.ResumeStep1Contract.Event as Step1Event
import java.time.LocalDate

@Composable
fun ResumeStep1Screen(viewModel: ResumeStep1ViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val effectFlow = viewModel.effect
    val focusManager = LocalFocusManager.current

    var isNameTextFieldFocused by rememberSaveable { mutableStateOf(false) }
    var isCerTificateTextFieldFocused by rememberSaveable { mutableStateOf(false) }

    val pickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            context.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        uri?.let { viewModel.setEvent(Step1Event.GetImageFromGallery(uri)) }
    }

    // Photo Picker 미지원 기기 fallback (거의 드물지만 대비)
    val getContentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            viewModel.setEvent(Step1Event.GetImageFromGallery(it))
        }
    }

    //요청할 권한
    val isPhotoPickerAvailable = remember {
        ActivityResultContracts.PickVisualMedia.isPhotoPickerAvailable()
    }

    LaunchedEffect(true) {
        effectFlow.collect { effect ->
            when (effect) {
                is Step1Effect.OpenGallery -> {
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

    if (uiState.showDatePickerSheet) {
        dateSpinnerBottomSheet(
            context = context,
            onConfirm = { picked ->
                viewModel.setEvent(Step1Event.SetBirthDate(picked))
                viewModel.setEvent(Step1Event.UpDateDatePickerSheet(!uiState.showDatePickerSheet))
            },
            onDismissRequest = { viewModel.setEvent(Step1Event.UpDateDatePickerSheet(false)) },
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
                        .clickable { viewModel.setEvent(Step1Event.OpenGallery) },
                    painter = painterResource(id = R.drawable.imageupload),
                    contentDescription = null,
                )
            } else {
                val galleryPhotoPainter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context)
                        .data(uiState.imageProfileUri)
                        .placeholder(R.drawable.imageupload)
                        .build()
                )
                Image(
                    painter = galleryPhotoPainter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(96.dp)
                        .padding(top = 16.dp)
                        .clickable { viewModel.setEvent(Step1Event.OpenGallery) }
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
                    viewModel.setEvent(Step1Event.SetUserName(it))
                },
                textStyle = NonggleTheme.typography.b1_main,
                textColor = Color.Black,
                trailingIcon = {
                    if (uiState.userName.isNotEmpty() && isNameTextFieldFocused) {
                        NonggleIconButton(
                            ImageResourceId = R.drawable.xcircle,
                            onClick = {
                                viewModel.setEvent(Step1Event.ClearUserName)
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
                        viewModel.setEvent(Step1Event.UpDateDatePickerSheet(true))
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
                        viewModel.setEvent(Step1Event.SetGenderType(context.getString(
                                    R.string.여
                                )))
                    },
                    genderSelectedMap = uiState.genderSelectedMap
                )
                genderSelectButton(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight(),
                    gender = context.getString(R.string.남),
                    selectGender = {
                        viewModel.setEvent(Step1Event.SetGenderType(context.getString(R.string.남)))
                    },
                    genderSelectedMap = uiState.genderSelectedMap
                )
            }
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = context.getString(R.string.자격증),
                style = NonggleTheme.typography.b2_sub,
                color = NonggleTheme.colors.g1
            )
            Row(
                modifier = Modifier.padding(top = 12.dp, bottom = 12.dp)
            ) {
                certificationButton(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight()
                        .padding(end = 16.dp),
                    title = context.getString(R.string.있음),
                    changeCertificateState = {
                        viewModel.setEvent(Step1Event.SetCertificateAvailable(context.getString(R.string.있음)))
                    },
                    certificateAvailable = uiState.certificationPossessionSelectedMap
                )
                certificationButton(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight(),
                    title = context.getString(R.string.없음),
                    changeCertificateState = {
                        viewModel.setEvent(Step1Event.SetCertificateAvailable(context.getString(R.string.없음)))
                    },
                    certificateAvailable = uiState.certificationPossessionSelectedMap
                )
            }
            if (uiState.certificationPossessionSelectedMap[context.getString(R.string.있음)] == true) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                        .padding(bottom = if(uiState.userCertificationList.isNotEmpty()) 12.dp else 40.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NonggleTextField(
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentHeight()
                            .onFocusChanged { focusState ->
                                isCerTificateTextFieldFocused = focusState.isFocused
                            },
                        textFieldType = TextFieldType.Standard,
                        value = uiState.userCertificateType,
                        onValueChange = {
                            viewModel.setEvent(Step1Event.SetUserCertificateDetail(it))
                        },
                        textStyle = NonggleTheme.typography.b1_main,
                        textColor = Color.Black,
                        trailingIcon = {
                            if (uiState.userCertificateType.isNotEmpty() && isCerTificateTextFieldFocused) {
                                NonggleIconButton(
                                    ImageResourceId = R.drawable.xcircle,
                                    onClick = {
                                        viewModel.setEvent(Step1Event.ClearUserCertificateDetail)
                                    }
                                )
                            }
                        },
                        placeholder = {
                            Text(
                                text = context.getString(R.string.자격증_입력),
                                style = NonggleTheme.typography.b1_main,
                                color = NonggleTheme.colors.g3,
                            )
                        },
                    )
                    //자격증 추가 버튼
                    ContainedButton(
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight(),
                        contentPadding = PaddingValues(horizontal = 30.dp, vertical = 13.dp),
                        enabled = uiState.userCertificateType.isNotEmpty(),
                        onClick = {
                            viewModel.setEvent(
                                Step1Event.AddCertificationChip(
                                    uiState.userCertificateType
                                )
                            )
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
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 200.dp),
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    items(
                        count = uiState.userCertificationList.size,
                    ) { index ->
                        certificationChipItem(
                            title = uiState.userCertificationList[index],
                            removeChip = {
                                viewModel.setEvent(Step1Event.RemoveCertificationChip(uiState.userCertificationList[index]))
                            }
                        )
                    }
                }
            }

        }
    }

}

