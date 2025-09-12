package com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.core.design_system.spoqahanSansneo
import com.capstone.nongglenonggle.core.noRippleClickable
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4.component.preferRegionChip
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4.component.workCategoryChip
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4.ResumeStep4Contract.Effect as Step4Effect
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4.ResumeStep4Contract.Event as Step4Event
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4.ResumeStep4Contract.State as Step4State

//class ResumeDFragment : BaseFragment<FragmentResumeDBinding>(R.layout.fragment_resume_d) {
//    private val viewModel: ResumeViewModel by activityViewModels()
//    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
//    var opensetting1 = ""
//    var opensetting2 = ""
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view= super.onCreateView(inflater, container, savedInstanceState)
//        return view
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding.viewModel = viewModel
//        val uid = firebaseAuth.currentUser?.uid!!
//
//        //기타품목 나타내기용
//        viewModel.categoryLive.observe(viewLifecycleOwner, Observer { list->
//            list[7].let{
//                if(it){
//                    binding.addtionalEdit.visibility = View.VISIBLE
//                }else{
//                    binding.addtionalEdit.visibility = View.GONE
//                }
//            }
//        })
//
//        binding.addtionalEdit.setOnFocusChangeListener{view,isfocus->
//            viewModel.setAdditional(isfocus)
//            if(isfocus && binding.addtionalEdit.text?.isNotEmpty() == true){
//                binding.addtionalEdit.showClearButton(R.drawable.xcircle)
//            }else{
//                binding.addtionalEdit.hideClearButton()
//            }
//        }
//        binding.addtionalEdit.addTextChangedListener(object :TextWatcher{
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                if (binding.addtionalEdit.isFocused && s?.isNotEmpty() == true) {
//                    binding.addtionalEdit.showClearButton(R.drawable.xcircle)
//                } else {
//                    binding.addtionalEdit.hideClearButton()
//                }
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                viewModel.focusAdditional.observe(viewLifecycleOwner){focus->
//                    if(focus && binding.addtionalEdit.text != null){
//                        binding.addtionalEdit.showClearButton(R.drawable.xcircle)
//                    }else{
//                        binding.addtionalEdit.hideClearButton()
//                    }
//                }
//                viewModel.additionalTxt = s.toString()
//            }
//
//        })
//        binding.dormA.setOnClickListener{
//            viewModel._clickDormA.postValue(true)
//            viewModel._clickDormB.postValue(false)
//            viewModel.dormType = binding.dormA.text.toString()
//        }
//        binding.dormB.setOnClickListener{
//            viewModel._clickDormA.postValue(false)
//            viewModel._clickDormB.postValue(true)
//            viewModel.dormType = binding.dormB.text.toString()
//        }
//        initSpinner(binding.spinner, R.array.select_resume_day, "희망하는 근무요일을 선택해주세요.", viewModel._activedaySpinner)
//
//        binding.spinnerEdit.setOnFocusChangeListener{view,focus->
//            viewModel.setEditFocus(focus)
//            if(focus && binding.spinnerEdit.text?.isNotEmpty() == true){
//                binding.spinnerEdit.showClearButton(R.drawable.xcircle)
//            }else{
//                binding.spinnerEdit.hideClearButton()
//            }
//        }
//        binding.spinnerEdit.addTextChangedListener(object : TextWatcher{
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                if(binding.spinnerEdit.isFocused && s?.isNotEmpty() == true ){
//                    binding.addtionalEdit.showClearButton(R.drawable.xcircle)
//                }else{
//                    binding.addtionalEdit.hideClearButton()
//                }
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                viewModel.focusSpinnerEdit.observe(viewLifecycleOwner){isfocus->
//                    if(isfocus && binding.spinnerEdit.text!=null){
//                        binding.spinnerEdit.showClearButton(R.drawable.xcircle)
//                    }else{
//                        binding.spinnerEdit.hideClearButton()
//                    }
//                }
//                viewModel.dayDetailData = s.toString()
//            }
//
//        })
//        binding.privateBtn.setOnClickListener{
//            viewModel._activeprivate.postValue(true)
//            viewModel._activepublic.postValue(false)
//            //비공개 설정시 추가적 기능 명세
//            opensetting1 = "private"
//            opensetting2 = "privateresume"
//        }
//        binding.publicBtn.setOnClickListener{
//            viewModel._activeprivate.postValue(false)
//            viewModel._activepublic.postValue(true)
//            opensetting1 = "public"
//            opensetting2 = "publicResume"
//        }
//        binding.selectLocation.setOnTouchListener{view,event->
//            //희망 장소 선택
//            if(event.action == MotionEvent.ACTION_UP){
//                showLocationSelect()
//            }
//            false
//        }
//
//        viewModel.locationSelect.observe(viewLifecycleOwner){list->
//            if(list.size == 0){
//                binding.flexbox.visibility = View.GONE
//            }
//            if(list.size ==2){
//                binding.flexbox.visibility = View.VISIBLE
//                binding.locationA.visibility = View.VISIBLE
//                binding.locationATxt.text = "${viewModel.locationSelect.value?.get(0)} ${viewModel.locationSelect.value?.get(1)}"
//            }
//            if(list.size == 4){
//                binding.locationB.visibility = View.VISIBLE
//                binding.locationBTxt.text = "${viewModel.locationSelect.value?.get(2)} ${viewModel.locationSelect.value?.get(3)}"
//            }
//            if(list.size==6){
//                binding.locationC.visibility = View.VISIBLE
//                binding.locationCTxt.text = "${viewModel.locationSelect.value?.get(4)} ${viewModel.locationSelect.value?.get(5)}"
//            }
//        }
//        binding.complete.setOnClickListener{
//            lifecycleScope.launch {
//                val result = viewModel.setResumeData()
//                if(result != null){
//                    viewModel.addResumeContent(result,opensetting1,opensetting2)
//                    Toast.makeText(context, "이력서 작성을 완료했습니다!", Toast.LENGTH_SHORT).show()
//                    activity?.finishAffinity()
//                    val intent = Intent(context,ResumeCompleteActivity::class.java)
//                    intent.putExtra("setting1", opensetting1)
//                    intent.putExtra("setting2", opensetting2)
//                    intent.putExtra("UID_KEY", uid)
//                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                    startActivity(intent)
//                }
//                else{
//                }
//            }
//            }
//    }
//    // 스피너 어댑터 초기화 함수
//    private fun initSpinner(spinner: Spinner, itemsArrayId: Int, hintText: String, viewModelLiveData: MutableLiveData<Boolean>) {
//        val items = resources.getStringArray(itemsArrayId)
//        val adapter = com.capstone.nongglenonggle.presentation.view.adapter.SpinnerAdapter(requireContext(), R.layout.item_spinner, items, R.id.list_content)
//        adapter.setHintTextColor(hintText, R.color.g3)
//        spinner.adapter = adapter
//        spinner.setSelection(adapter.count)
//
//        spinner.post {
//            spinner.dropDownVerticalOffset = spinner.height
//        }
//
//        spinner.setOnTouchListener { v, event ->
//            if (event.action == MotionEvent.ACTION_UP) {
//                viewModelLiveData.postValue(true)
//                binding.spinnerEdit.visibility = View.VISIBLE
//            }
//            false
//        }
//
//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                viewModelLiveData.postValue(false)
//                viewModel.dayData = items[p2]
//                p1?.isPressed = false
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//                viewModelLiveData.postValue(false)
//            }
//        }
//    }
//
//    private fun showLocationSelect() {
//        val newFrament = LocationSelectFragment()
//        newFrament.show(parentFragmentManager,"locationselectfragment")
//    }
//
//}

@Composable
fun ResumeStep4Screen(
    viewModel: ResumeStep4ViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val effectFlow = viewModel.effect

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
                text = context.getString(R.string.희망_근무지역),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = NonggleTheme.colors.g1,
                    fontWeight = FontWeight.Normal,
                    fontFamily = spoqahanSansneo
                )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .border(
                        BorderStroke(1.dp, NonggleTheme.colors.g_line),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .noRippleClickable {
                        viewModel.setEvent(event = Step4Event.ShowRegionBottomSheet)
                    },

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = context.getString(R.string.희망_근무지역),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = spoqahanSansneo,
                            color = NonggleTheme.colors.g3
                        )
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        modifier = Modifier.size(size = 24.dp),
                        painter = painterResource(R.drawable.place),
                        contentDescription = null
                    )
                }
            }
        }
        item {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 200.dp)
                    .padding(top = 12.dp),
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(
                    count = uiState.preferLocationList.size,
                ) {
                    preferRegionChip(
                        onClick = {
                            viewModel.setEvent(event = Step4Event.RemovePreferLocation(region = "")) //FIXME: bottomsheet으로부터 오는 지역 값 넣어야함
                        },
                        preferLocation = ""
                    )
                }
            }
        }
        item {
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = context.getString(R.string.희망_품목),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = spoqahanSansneo,
                    color = NonggleTheme.colors.g1
                )
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = context.getString(R.string.다중_선택이),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = spoqahanSansneo,
                    color = NonggleTheme.colors.g2
                )
            )
        }
        item { //품목
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 200.dp)
                    .padding(top = 12.dp),
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(
                    count = uiState.totalPreferWorkCategoryMap.size,
                ) { index ->
                    val entry = uiState.totalPreferWorkCategoryMap.entries(index)
//                    workCategoryChip(
//                        categoryTitle = uiState.totalPreferWorkCategoryList.,
//                        onClick = TODO(),
//                        categoryActivate = TODO()
//                    )
                }
            }
        }
    }
}