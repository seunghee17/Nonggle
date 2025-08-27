package com.capstone.nongglenonggle.presentation.view.worker.resume

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.viewpager2.widget.ViewPager2
import coil.compose.AsyncImage
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.addFocusCleaner
import com.capstone.nongglenonggle.core.common.button.ImageButton
import com.capstone.nongglenonggle.core.common.button.NonggleIconButton
import com.capstone.nongglenonggle.core.common.button.OutlinedButton
import com.capstone.nongglenonggle.core.common.textfield.NonggleTextField
import com.capstone.nongglenonggle.core.common.textfield.TextFieldType
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.core.design_system.spoqahanSansneo
import com.capstone.nongglenonggle.core.noRippleClickable
import com.capstone.nongglenonggle.databinding.FragmentResumeABinding
import com.capstone.nongglenonggle.presentation.base.BaseFragment
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint
import com.capstone.nongglenonggle.presentation.util.hideClearButton
import com.capstone.nongglenonggle.presentation.util.showClearButton
import com.capstone.nongglenonggle.presentation.view.dialog.DatepickerFragment

@AndroidEntryPoint
class ResumeAFragment : BaseFragment<FragmentResumeABinding>(R.layout.fragment_resume_a) {
    private val viewModel: ResumeViewModel by activityViewModels()
    var pickImageFromAlbum = 1
    var fbStorage: FirebaseStorage? = null
    var uriPhoto: Uri? = null
    private val REQUEST_CODE_PERMISSION = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fbStorage = FirebaseStorage.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        binding.profileUpload.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                openGallery()
            } else {
                //권한 요청
                requestStoragePermission()
            }
        }
        return view
    }

    private fun openGallery() {
        //open album
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, pickImageFromAlbum)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        binding.nameEdit.setOnFocusChangeListener { view, isfocus ->
            viewModel.setFocus(isfocus)
            if (isfocus && binding.nameEdit.text?.isNotEmpty() == true) {
                binding.nameEdit.showClearButton(R.drawable.xcircle)
            } else {
                binding.nameEdit.hideClearButton()
            }
        }
        binding.nameEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.nameEdit.isFocused && s?.isNotEmpty() == true) {
                    binding.nameEdit.showClearButton(R.drawable.xcircle)
                } else {
                    binding.nameEdit.hideClearButton()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.changeFocus.observe(viewLifecycleOwner) { isfocus ->
                    if (isfocus && binding.nameEdit.text != null) {
                        binding.nameEdit.showClearButton(R.drawable.xcircle)
                    } else {
                        binding.nameEdit.hideClearButton()
                    }
                }
                viewModel.userName = s.toString()
            }

        })

        //생년월일
        binding.birthContainer.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                showDatePicker()
            }
            false
        }
        viewModel.BirthList.observe(viewLifecycleOwner) { isdata ->
            binding.birthTxt.text =
                "${viewModel.BirthList.value?.get(0)}년 ${viewModel.BirthList.value?.get(1)}월 ${
                    viewModel.BirthList.value?.get(2)
                }일"
            viewModel.setActive()
            viewModel.calculate(viewModel.BirthList.value?.get(0)!!)
        }
        binding.certifiYes.setOnClickListener {
            viewModel.activeCertifiA()
        }
        binding.certifiNo.setOnClickListener {
            viewModel.activeCertifiB()
        }

        binding.women.setOnClickListener {
            viewModel.activeWomen()
        }
        binding.man.setOnClickListener {
            viewModel.activeMan()
        }

        binding.certificationTxt.setOnFocusChangeListener { view, isfocus ->
            viewModel.setFocusCertifi(isfocus)
            if (isfocus && binding.certificationTxt.text?.isNotEmpty() == true) {
                binding.certificationTxt.showClearButton(R.drawable.xcircle)
            } else {
                binding.certificationTxt.hideClearButton()
            }
        }
        binding.certificationTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.certificationTxt.isFocused && s?.isNotEmpty() == true) {
                    binding.certificationTxt.showClearButton(R.drawable.xcircle)
                    viewModel._changeConfirmCertifi.postValue(true)
                } else {
                    binding.certificationTxt.hideClearButton()
                    viewModel._changeConfirmCertifi.postValue(false)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.changeFocusCertifi.observe(viewLifecycleOwner) { isfocus ->
                    if (isfocus && binding.certificationTxt.text != null) {
                        binding.certificationTxt.showClearButton(R.drawable.xcircle)
                        viewModel._changeConfirmCertifi.postValue(true)
                    } else {
                        binding.certificationTxt.hideClearButton()
                        viewModel._changeConfirmCertifi.postValue(false)
                    }
                }
                binding.confirmBtn.setOnClickListener {
                    viewModel.storeCarrer(s.toString())
                    binding.certificationTxt.text.clear()
                }
            }


        })

        viewModel.CarrerList.observe(viewLifecycleOwner, { list ->
            if (list.isNotEmpty() && list.size == 1) {
                binding.flexBox.visibility = View.VISIBLE
                binding.certifiA.visibility = View.VISIBLE
                binding.certifiATxt.text = viewModel.CarrerList.value?.get(0)
            } else if (list.isNotEmpty() && list.size == 2) {
                binding.certifiB.visibility = View.VISIBLE
                binding.certifiBTxt.text = viewModel.CarrerList.value?.get(1)
            } else if (list.isNotEmpty() && list.size == 3) {
                binding.certifiC.visibility = View.VISIBLE
                binding.certifiCTxt.text = viewModel.CarrerList.value?.get(2)
            } else if (list.isEmpty()) {
                binding.flexBox.visibility = View.GONE
            }
        })
        binding.certifiAClose.setOnClickListener {
            viewModel.removeCarrer(0)
        }
        binding.certifiBClose.setOnClickListener {
            viewModel.removeCarrer(1)
            binding.certifiB.visibility = View.GONE
        }
        binding.certifiCClose.setOnClickListener {
            viewModel.removeCarrer(2)
            binding.certifiC.visibility = View.GONE
        }
        binding.nextBtn.setOnClickListener {
            val viewpager = requireActivity().findViewById<ViewPager2>(R.id.viewpager)
            val current = viewpager.currentItem
            val next = current + 1
            if (next < viewpager.adapter?.itemCount ?: 0) {
                viewpager.setCurrentItem(next, true)
            } else {
            }
        }

    }

    private fun showDatePicker() {
        val newFrament = DatepickerFragment()
        newFrament.show(parentFragmentManager, "datepicker")
    }


    //권한이 없을때 해당함수 호출
    private fun requestStoragePermission() {
        requestPermissions(
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_CODE_PERMISSION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            } else {
                //권한 거부처리
                openGallery()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImageFromAlbum) {
            if (resultCode == Activity.RESULT_OK) {
                uriPhoto = data?.data
                //화면에 사진을 보여준다
                binding.profileUpload.setImageURI(uriPhoto)
                uriPhoto?.let { uri ->
                    //val imageEntity = Model.ImageEntity(uri)
                    //viewModel.uploadImage(imageEntity)
                }
            } else {
                //예외처리
            }
        }
    }


}

@Composable
fun ResumeStep1Screen(
    viewModel: WorkerResumeComposeViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val effectFlow = viewModel.effect
    val focusManager = LocalFocusManager.current

    var isNameTextFieldFocused by rememberSaveable { mutableStateOf(false) }
    var isCerTificateTextFieldFocused by rememberSaveable { mutableStateOf(false) }

    val pickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let { viewModel.onImagePicked(it) } // VM이 Uri를 받아 업로드까지 처리
    }

    // Photo Picker 미지원 기기 fallback (거의 드물지만 대비)
    val getContentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> uri?.let(viewModel::onImagePicked) }

    //요청할 권한
    val isPhotoPickerAvailable = remember {
        ActivityResultContracts.PickVisualMedia.isPhotoPickerAvailable()
    }

    LaunchedEffect(Unit) {
        effectFlow.collect { effect ->
            when (effect) {
                is WorkerResumeContract.Effect.OpenGallery -> {
                    if (isPhotoPickerAvailable) {
                        pickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    } else {
                        // 구형 기기/환경 fallback
                        getContentLauncher.launch("image/*")
                    }
                }
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .addFocusCleaner(focusManager = focusManager)
    ) {
        item {
            Text(
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
                        .noRippleClickable {
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
                value = uiState.addressTextfieldData,
                onValueChange = {
                    viewModel.setEvent(WorkerResumeContract.Event.InputAddressDetail(it))
                },
                textStyle = NonggleTheme.typography.b1_main,
                textColor = Color.Black,
                trailingIcon = {
                    if (uiState.addressTextfieldData.isNotEmpty() && isNameTextFieldFocused) {
                        NonggleIconButton(
                            ImageResourceId = R.drawable.xcircle,
                            onClick = {
                                viewModel.setEvent(WorkerResumeContract.Event.ClearAddressData)
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

            ) {
                Row {
                    Text(
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp),
                        text = context.getString(R.string.생년월일을_선택),
                        style = NonggleTheme.typography.b4_btn,
                        textAlign = TextAlign.Start
                        //color =  생년월일 유무에 따라 다른 색상 배정
                    )
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
                            WorkerResumeContract.Event.SetGenderType(
                                context.getString(
                                    R.string.여
                                )
                            )
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
                            WorkerResumeContract.Event.SetGenderType(
                                context.getString(
                                    R.string.남
                                )
                            )
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
                        viewModel.setEvent(WorkerResumeContract.Event.ChangeCertificateState(value = true))
                    },
                    certificateAvailable = uiState.haveCertification ?: false
                )
                certificationButton(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight(),
                    title = context.getString(R.string.없음),
                    changeCertificateState = {
                        viewModel.setEvent(WorkerResumeContract.Event.ChangeCertificateState(value = false))
                    },
                    certificateAvailable = uiState.haveCertification ?: false
                )
            }
//            Row {
//                NonggleTextField(
//                    modifier = Modifier
//                        .padding(top = 12.dp, end = 16.dp)
//                        .weight(1f)
//                        .wrapContentHeight()
//                        .onFocusChanged { focusState ->
//                            isCerTificateTextFieldFocused = focusState.isFocused
//                        },
//                    textFieldType = TextFieldType.Standard,
//                    value = doroAddressDetail,
//                    onValueChange = onValueChange,
//                    textStyle = NonggleTheme.typography.b1_main,
//                    textColor = Color.Black,
//                    trailingIcon = {
//                        if (doroAddressDetail.isNotEmpty() && isCerTificateTextFieldFocused) {
//                            NonggleIconButton(
//                                ImageResourceId = R.drawable.xcircle,
//                                onClick = clearValueAction
//                            )
//                        }
//                    },
//                    placeholder = {
//                        Text(
//                            text = context.getString(R.string.본인의_이름을),
//                            style = NonggleTheme.typography.b1_main,
//                            color = NonggleTheme.colors.g3,
//                        )
//                    },
//                )
//                ContainedButton(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .wrapContentHeight(),
//                    enabled = ,
//                    onClick = {},
//                    titleText = context.getString(R.string.확인),
//                    titleTextStyle = TextStyle(
//                        fontFamily = spoqahanSansneo,
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Medium,
//                        color = Color.White
//                    ),
//                )
//            }

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
            modifier = modifier
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
//            ImageButton(
//                onClick = removeChip
//            )
        }
    }
}