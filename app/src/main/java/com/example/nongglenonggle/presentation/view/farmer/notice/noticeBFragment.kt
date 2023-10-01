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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.example.nongglenonggle.R
import com.example.nongglenonggle.presentation.base.BaseFragment
import com.example.nongglenonggle.databinding.FragmentNoticeBBinding
import com.example.nongglenonggle.domain.entity.Model
import com.example.nongglenonggle.presentation.view.adapter.SpinnerAdapter
import com.example.nongglenonggle.presentation.viewModel.farmer.FarmerNoticeViewModel
import com.google.firebase.auth.FirebaseAuth
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
            if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                //open album
                var photoPickerIntent = Intent(Intent.ACTION_PICK)
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

        val daySpinner = listOf("주 1일", "주 2일", "주 3일", "주 4일","주 5일","주 6일","주 7일", "요일협의")

        binding.daySelectSpinner.adapter = SpinnerAdapter(requireContext(), R.layout.item_spinner, daySpinner)

        binding.daySelectSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val value = binding.daySelectSpinner.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?)
            {

            }

        }

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
                binding.startDate.text = "${viewModel.DateList.value?.get(0)}년 ${viewModel.DateList.value?.get(1)}월 ${viewModel.DateList.value?.get(2)}일"
                binding.datepicker1.hint=""
            }
            if(viewModel.DateList.value?.size == 6){
                binding.endDate.text = "${viewModel.DateList.value?.get(3)}년 ${viewModel.DateList.value?.get(4)}월 ${viewModel.DateList.value?.get(5)}일"
                binding.datepicker2.hint=""
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

    private fun showDatePicker()
    {
        val newFrament = DatepickerFragment()
        newFrament.show(parentFragmentManager,"datepicker")
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

//    private fun ImageUpload(view: View){
//        Log.e("image", "이미지 업로드 완1")
//        val user = FirebaseAuth.getInstance().currentUser
//        val uid = user?.uid
//        var imageFileName = "Image_" + uid + "_.png"
//        var storageRef = fbStorage?.reference?.child("NoticeImages")?.child(imageFileName)
//
//        storageRef?.putFile(uriPhoto!!)?.addOnSuccessListener {
//            Log.e("image", "이미지 업로드 완")
//
//            storageRef.downloadUrl.addOnSuccessListener { url->
//                viewModel._farmerImage.value = url.toString()
//            }
//        }
//    }


}