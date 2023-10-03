package com.example.nongglenonggle.presentation.view.farmer.notice

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.nongglenonggle.R
import com.example.nongglenonggle.presentation.base.BaseFragment
import com.example.nongglenonggle.databinding.FragmentNoticeBBinding
import com.example.nongglenonggle.domain.entity.Model
import com.example.nongglenonggle.presentation.view.adapter.SpinnerAdapter
import com.example.nongglenonggle.presentation.view.dialog.DatepickerFragment
import com.example.nongglenonggle.presentation.view.dialog.TimepickerFragment
import com.example.nongglenonggle.presentation.viewModel.farmer.FarmerNoticeViewModel
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class noticeBFragment : BaseFragment<FragmentNoticeBBinding>(R.layout.fragment_notice_b) {
    private val viewModel: FarmerNoticeViewModel by activityViewModels()
    var pickImageFromAlbum = 0
    var fbStorage : FirebaseStorage? = null
    var uriPhoto : Uri? = null
    private val REQUEST_CODE_PERMISSION = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= super.onCreateView(inflater, container, savedInstanceState)
        //사진 업로드
        //storage초기화
        fbStorage = FirebaseStorage.getInstance()
        binding.workImageA.setOnClickListener{
            if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                //open album
                val photoPickerIntent = Intent(Intent.ACTION_PICK)
                photoPickerIntent.type = "image/*"
                startActivityForResult(photoPickerIntent, pickImageFromAlbum)
            }
            else{
                //권한 요청
                requestStoragePermission()
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel




        //시간입력 버튼
        val workerTime1 = binding.workerTime1
        val workerTime2 = binding.workerTime2



        val category1 = binding.farmerCategory1
        val category2 = binding.farmerCategory2
        val category3 = binding.farmerCategory3
        val category4 = binding.farmerCategory4
        val category5 = binding.farmerCategory5
        val category6 = binding.farmerCategory6
        val category7 = binding.farmerCategory7
        val category8 = binding.farmerCategory8

        val list = ArrayList<Button>()
        val list2= listOf(category1,category2,category3,category4,category5,category6,category7,category8)
        list.addAll(list2)
        var clickcnt =0

        binding.titleEdit.setOnFocusChangeListener { view, isfocus ->
            viewModel._fragmentBState.postValue(isfocus)
        }

        binding.titleEdit.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.titleEdit.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.xcircle,0)
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.fragmentBState.observe(viewLifecycleOwner){isfocus->
                    if(isfocus && binding.titleEdit.text != null){
                        binding.titleEdit.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.xcircle,0)
                        binding.titleEdit.getClearButton(R.drawable.xcircle)
                    }
                    else{
                        binding.titleEdit.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
                    }
                }
            }
        })



        workerTime1.setOnClickListener{
            viewModel.setActiveTime1Button()
        }

        workerTime2.setOnClickListener{
            viewModel.setActiveTime2Button()
        }

        val buttonClickListener = View.OnClickListener {
            view->
            if(clickcnt < 3){
                clickcnt++
                when(viewModel.category.value){
                    true -> {
                        viewModel.setActiveButton()
                    }
                    false->
                    {
                        viewModel.setActiveButton()
                    }
                    null ->{

                    }
                }
            }
        }

        //when으로 바꾸기
        for(i in 0..7)
        {
            list[i].setOnClickListener(buttonClickListener)
        }

        //spinner 구현 부분

        val dayItems = resources.getStringArray(R.array.select_day)
        val dayadapter = SpinnerAdapter(requireContext(), R.layout.item_spinner, dayItems,R.id.list_content)
        dayadapter.setHintTextColor("근무 요일을 선택해주세요.", R.color.g3)
        binding.daySelectSpinner.adapter = dayadapter
        binding.daySelectSpinner.setSelection(dayadapter.count)
        binding.daySelectSpinner.post{
            binding.daySelectSpinner.dropDownVerticalOffset = binding.daySelectSpinner.height
        }

        binding.daySelectSpinner.setOnTouchListener{
            v,event->
            if(event.action == MotionEvent.ACTION_UP){
                viewModel._activeWorkDay.postValue(true)
            }
            false
        }

        binding.daySelectSpinner.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //database들어갈용 세팅!!!!!!!!
                viewModel._activeWorkDay.postValue(false)
                p1?.isPressed = false
                viewModel._daytextVisible.postValue(true)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                viewModel._activeWorkDay.postValue(false)
            }

        }

        binding.daySelectTxt.setOnFocusChangeListener{view,isfocus->
            viewModel._dayTextActive.postValue(isfocus)
        }

        binding.daySelectTxt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.daySelectTxt.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.xcircle,0)
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.dayTextActive.observe(viewLifecycleOwner){isfocus->
                    if(isfocus && binding.daySelectTxt.text != null){
                        binding.daySelectTxt.getClearButton(R.drawable.xcircle)
                    }else{
                        binding.daySelectTxt.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
                    }
                }
            }

        })

        binding.datepicker1.setOnTouchListener{v,event->
            if(event.action == MotionEvent.ACTION_UP)
            {
                showDatePicker()
            }
            false
        }

        //작업 시작날짜 종료날짜 text에 표시하기위한 옵저빙
        viewModel.DateList.observe(viewLifecycleOwner){havedata->
            if(viewModel.DateList.value?.size == 3){
                binding.datepicker1Txt.text = "${viewModel.DateList.value?.get(0)}년 ${viewModel.DateList.value?.get(1)}월 ${viewModel.DateList.value?.get(2)}일"
                viewModel._datepickerTextA.value = true
            }
            if(viewModel.DateList.value?.size == 6){
                binding.datepicker2Txt.text = "${viewModel.DateList.value?.get(3)}년 ${viewModel.DateList.value?.get(4)}월 ${viewModel.DateList.value?.get(5)}일"
                viewModel._datepickerTextB.value = true
            }
        }

        binding.datepicker2.setOnTouchListener{v,event->
            if(event.action == MotionEvent.ACTION_UP)
            {
                showDatePicker()
            }
            false
        }

        //업무 세부 내용
        binding.noticeContent.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel._detailContent.value = p0.toString()
            }

        })

        binding.workTimeStart.setOnClickListener{
            showTimePicker()
        }
        binding.workTimeEnd.setOnClickListener{
            showTimePicker()
        }

        viewModel.TimeList.observe(viewLifecycleOwner){havedata->
            if(viewModel.TimeList.value?.size==3){
                viewModel._haveStartData.postValue(true)
                binding.workTimeStartTxt.text = "${viewModel.TimeList.value?.get(0)} ${viewModel.TimeList.value?.get(1)}:${viewModel.TimeList.value?.get(2)}"
            }
            if(viewModel.TimeList.value?.size==6){
                viewModel._haveEndData.postValue(true)
                binding.workTimeEndTxt.text = "${viewModel.TimeList.value?.get(3)} ${viewModel.TimeList.value?.get(4)}:${viewModel.TimeList.value?.get(5)}"
            }
        }

        binding.nextBtn.setOnClickListener{
            val viewpager = requireActivity().findViewById<ViewPager2>(R.id.viewpager)
            val current = viewpager.currentItem
            val next = current+1
            if(next < viewpager.adapter?.itemCount ?: 0){
                viewpager.setCurrentItem(next,true)
            }else{
                Log.e("yet","아직 마지막아님")
            }
        }

    }

    fun EditText.getClearButton(drawableRightId:Int){
        val drawableRight = 2
        val rightDrawable = this.compoundDrawables[drawableRight]
        if (rightDrawable != null) {
            val drawableWidth = rightDrawable.bounds.width()
            val clearButtonStart = this.width - drawableWidth

            this.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= clearButtonStart) {
                        this.text.clear()
                        return@setOnTouchListener true
                    }
                }
                false
            }
        }
    }

    private fun showDatePicker() {
        val newFrament = DatepickerFragment()
        newFrament.show(parentFragmentManager,"datepicker")
    }

    private fun showTimePicker(){
        val newFragment = TimepickerFragment()
        newFragment.show(parentFragmentManager,"timepicker")
    }

    //권한이 없을때 해당함수 호출
    private fun requestStoragePermission(){
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_CODE_PERMISSION
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == pickImageFromAlbum){
            if(resultCode == Activity.RESULT_OK){
                uriPhoto = data?.data
                //화면에 사진을 보여준다
                binding.workImageA.setImageURI(uriPhoto)
                uriPhoto?.let{uri->
                    val imageEntity = Model.ImageEntity(uri)
                    viewModel.uploadImage(imageEntity)
                }
            }
            else{

            }
        }
    }



}