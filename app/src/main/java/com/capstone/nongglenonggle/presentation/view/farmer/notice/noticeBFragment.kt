package com.capstone.nongglenonggle.presentation.view.farmer.notice

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.presentation.base.BaseFragment
import com.capstone.nongglenonggle.databinding.FragmentNoticeBBinding
import com.capstone.nongglenonggle.presentation.view.adapter.SpinnerAdapter
import com.capstone.nongglenonggle.presentation.view.dialog.HireDatePickerFragment
import com.capstone.nongglenonggle.presentation.view.dialog.TimepickerFragment
import com.capstone.nongglenonggle.presentation.viewModel.farmer.FarmerNoticeViewModel
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
        fbStorage = FirebaseStorage.getInstance()
        binding.workImageA.setOnClickListener{
            if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED){
                val photoPickerIntent = Intent(Intent.ACTION_PICK)
                photoPickerIntent.type = "image/*"
                startActivityForResult(photoPickerIntent, pickImageFromAlbum)
            }
            else{
                val photoPickerIntent = Intent(Intent.ACTION_PICK)
                photoPickerIntent.type = "image/*"
                startActivityForResult(photoPickerIntent, pickImageFromAlbum)
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

        binding.titleEdit.setOnFocusChangeListener { view, isfocus ->
            viewModel._fragmentBState.postValue(isfocus)
        }

        binding.titleEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.titleEdit.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.xcircle, 0)
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.fragmentBState.observe(viewLifecycleOwner) { isfocus ->
                    if (isfocus && binding.titleEdit.text != null) {
                        binding.titleEdit.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.xcircle, 0)
                        binding.titleEdit.getClearButton(R.drawable.xcircle)
                    } else {
                        binding.titleEdit.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                    }
                }
                viewModel.titleInfo = p0.toString()
            }
        })



        workerTime1.setOnClickListener {
            viewModel._workerTime1.postValue(true)
            viewModel._workerTime2.postValue(false)
            viewModel.timeType = "시간입력"
        }

        workerTime2.setOnClickListener {
            viewModel._workerTime1.postValue(false)
            viewModel._workerTime2.postValue(true)
            viewModel.timeType = "시간협의"
        }


        val dayItems = resources.getStringArray(R.array.select_day)
        val dayadapter = SpinnerAdapter(requireContext(), R.layout.item_spinner, dayItems, R.id.list_content)
        dayadapter.setHintTextColor("근무 요일을 선택해주세요.", R.color.g3)
        binding.daySelectSpinner.adapter = dayadapter
        binding.daySelectSpinner.setSelection(dayadapter.count)
        binding.daySelectSpinner.post {
            binding.daySelectSpinner.dropDownVerticalOffset = binding.daySelectSpinner.height
        }

        binding.daySelectSpinner.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                viewModel._activeWorkDay.postValue(true)
            }
            false
        }

        binding.daySelectSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if(p2 == 8){
                        return
                    }
                    viewModel._activeWorkDay.postValue(false)
                    p1?.isPressed = false
                    viewModel._dayType.value = p0?.getItemAtPosition(p2).toString()
                    viewModel._daytextVisible.postValue(true)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    viewModel._activeWorkDay.postValue(false)
                }
            }

        binding.daySelectTxt.setOnFocusChangeListener { view, isfocus ->
            viewModel._dayTextActive.postValue(isfocus)
        }

        binding.daySelectTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.daySelectTxt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.xcircle, 0)
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.dayTextActive.observe(viewLifecycleOwner) { isfocus ->
                    if (isfocus && binding.daySelectTxt.text != null) {
                        binding.daySelectTxt.getClearButton(R.drawable.xcircle)
                    } else {
                        binding.daySelectTxt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                    }
                }
                viewModel._dayDetail.postValue(p0.toString())
            }

        })

        binding.datepicker1.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                showDatePicker()
            }
            false
        }
        //작업 시작날짜 종료날짜 text에 표시하기위한 옵저빙
        viewModel.DateList.observe(viewLifecycleOwner) { havedata ->
            if (viewModel.DateList.value?.size == 3) {
                binding.datepicker1Txt.text =
                    "${viewModel.DateList.value?.get(0)}년 ${viewModel.DateList.value?.get(1)}월 ${viewModel.DateList.value?.get(2)}일"
                viewModel._datepickerTextA.value = true
                val year = viewModel.DateList.value?.get(0)
                val shortYear = year!! % 100
                val currentList = viewModel._workPeriod.value ?: emptyList()
                viewModel._workPeriod.value = currentList + listOf(
                    "${shortYear}.${viewModel.DateList.value?.get(1)}.${
                        viewModel.DateList.value?.get(2)
                    }"
                )
            }
            if (viewModel.DateList.value?.size == 6) {
                binding.datepicker2Txt.text =
                    "${viewModel.DateList.value?.get(3)}년 ${viewModel.DateList.value?.get(4)}월 ${
                        viewModel.DateList.value?.get(5)
                    }일"
                viewModel._datepickerTextB.value = true
                val year = viewModel.DateList.value?.get(3)
                val shortYear = year!! % 100
                val currentList = viewModel._workPeriod.value ?: emptyList()
                viewModel._workPeriod.value = currentList + listOf(
                    "${shortYear}.${viewModel.DateList.value?.get(4)}.${
                        viewModel.DateList.value?.get(5)
                    }"
                )
            }
        }

        binding.datepicker2.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                showDatePicker()
            }
            false
        }

        //업무 세부 내용
        binding.noticeContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel._detailContent.value = p0.toString()
            }

        })

        binding.workTimeStart.setOnClickListener {
            showTimePicker()
        }
        binding.workTimeEnd.setOnClickListener {
            showTimePicker()
        }

        viewModel.TimeList.observe(viewLifecycleOwner) { data ->
            data?.let {
                if (it.size == 3) {
                    viewModel._haveStartData.postValue(true)
                    binding.workTimeStartTxt.text = "${it.get(0)} ${it.get(1)}:${it.get(2)}"
                    //데이터베이스에 저장하기 위한 준비
                    if (it[0] == "PM") {
                        val hour = (it[1].toInt() + 12).toString()
                        viewModel.timeDetail = "${hour}:${it[2]}"
                    } else {
                        //AM일 경우
                        viewModel.timeDetail = "${it[1]}:${it[2]}"
                    }
                }
                if (it.size == 6) {
                    viewModel._haveEndData.postValue(true)
                    binding.workTimeEndTxt.text = "${it.get(3)} ${it.get(4)}:${it.get(5)}"
                    if (it[3] == "PM") {
                        val hour = (it[4].toInt() + 12).toString()
                        viewModel.timeDetailA = "${hour}:${it[5]}"
                    } else {
                        //AM일 경우
                        viewModel.timeDetailA = "${it[4]}:${it[5]}"
                    }
                    viewModel.result = "${viewModel.timeDetail}~${viewModel.timeDetailA}"
                }
            }
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
        val newFrament = HireDatePickerFragment()
        newFrament.show(parentFragmentManager,"datepicker")
    }

    private fun showTimePicker(){
        val newFragment = TimepickerFragment()
        newFragment.show(parentFragmentManager,"timepicker")
    }

    //권한이 없을때 해당함수 호출
    private fun requestStoragePermission(){
        requestPermissions(
            //requireActivity(),
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
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
//                    val imageEntity = Model.ImageEntity(uri)
//                    viewModel.uploadImage(imageEntity)
                }
            }
            else{

            }
        }
    }


}