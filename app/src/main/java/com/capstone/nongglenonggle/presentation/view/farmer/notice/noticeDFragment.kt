package com.capstone.nongglenonggle.presentation.view.farmer.notice

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.databinding.FragmentNoticeDBinding
import com.capstone.nongglenonggle.presentation.base.BaseFragment
import com.capstone.nongglenonggle.presentation.view.dialog.HireDatePickerFragment
import com.capstone.nongglenonggle.presentation.viewModel.farmer.FarmerNoticeViewModel

class noticeDFragment : BaseFragment<FragmentNoticeDBinding>(R.layout.fragment_notice_d) {
    private val viewModel: FarmerNoticeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= super.onCreateView(inflater, container, savedInstanceState)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        //숙소제공 있음
        binding.dormYes.setOnClickListener{
            viewModel._activeDorm.postValue(true)
            viewModel._activeNoDorm.postValue(false)
        }
        //숙소제공 없음
        binding.dormNo.setOnClickListener{
            viewModel._activeDorm.postValue(false)
            viewModel._activeNoDorm.postValue(true)
            viewModel._DormType.value = "숙소를 지원하지 않습니다."
        }
        binding.yesDormInfo.setOnFocusChangeListener{view,isfocus->
            viewModel._yesDormActive.postValue(isfocus)
        }
        binding.yesDormInfo.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.yesDormInfo.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.xcircle,0)
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.yesDormActive.observe(viewLifecycleOwner){isfocus->
                    if(isfocus && binding.yesDormInfo.text != null){
                        binding.yesDormInfo.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.xcircle,0)
                        binding.yesDormInfo.getClearButton(R.drawable.xcircle)
                    }
                    else{
                        binding.yesDormInfo.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
                    }
                }
                viewModel._DormType.value = "숙소를 지원합니다. ${p0.toString()}"
            }

        })

        binding.foodYes.setOnClickListener{
            viewModel._yesFood.postValue(true)
            viewModel._noFood.postValue(false)
        }
        binding.foodNo.setOnClickListener{
            viewModel._yesFood.postValue(false)
            viewModel._noFood.postValue(true)
            viewModel._FoodType.value = "식사를 제공하지 않습니다." }

        binding.yesFoodInfo.setOnFocusChangeListener{view,isfocus->
            viewModel._yesFoodActive.postValue(isfocus)
        }
        binding.yesFoodInfo.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.yesFoodInfo.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.xcircle, 0)
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.yesFoodActive.observe(viewLifecycleOwner){isfocus->
                    if(isfocus && binding.yesFoodInfo.text != null){
                        binding.yesFoodInfo.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.xcircle, 0)
                        binding.yesFoodInfo.getClearButton(R.drawable.xcircle)
                    }
                    else{
                        binding.yesFoodInfo.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
                    }
                }
                viewModel._FoodType.value = "식사를 제공합니다. ${p0.toString()}"
            }

        })
        //마감기한 active용
        binding.deadlineYes.setOnClickListener{
            viewModel._yesDeadlineActive.postValue(true)
            viewModel._noDeadlineActive.postValue(false)
            viewModel._DeadlineType.postValue(binding.deadlineYesTxt.text.toString())
        }

        binding.deadlineNo.setOnClickListener{
            viewModel._yesDeadlineActive.postValue(false)
            viewModel._noDeadlineActive.postValue(true)
            viewModel._DeadlineType.postValue(binding.deadlineNoTxt.text.toString())
        }

        binding.deadlinePicker.setOnClickListener{
            //작업 시작날짜 끝나는날짜 뒤에 그냥 배치시켜서 내가 직접 인덱스 접근해서 데이터 뿌려주는 걸로
            showDatePicker()
        }

        //마감기한 데이터 띄우기
        viewModel.DateList.observe(viewLifecycleOwner){havedata->
            if(viewModel.DateList.value?.size == 9){
                viewModel._isDeadline.postValue(true)
                binding.deadlineTxt.text = "${viewModel.DateList.value?.get(6)}년 ${viewModel.DateList.value?.get(7)}월 ${viewModel.DateList.value?.get(8)}일"
                viewModel.deadlineDetail = "${viewModel.DateList.value?.get(6)}.${viewModel.DateList.value?.get(7)}.${viewModel.DateList.value?.get(8)}"
            }
            else{
                viewModel._isDeadline.postValue(false)
            }

        }

        binding.checkboxContainerA.setOnClickListener{
            binding.checkboxA.isChecked = true
        }
        binding.checkboxContainerB.setOnClickListener{
            binding.checkboxB.isChecked = true
            binding.nextBtn.setBackgroundResource(R.color.m1)
        }
        binding.nextBtn.setOnClickListener{
            val result = viewModel.setNoticeData()
            if(result != null){
                viewModel.addNoticeContent(result)
                goToNext()
            }
        }

        binding.preferential.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.preferential = s.toString()
            }

        })

    }

    private fun goToNext(){
        val intent = Intent(requireContext(),NoticeCompleteguideActivity::class.java)
        startActivity(intent)
    }

    //날짜 선택용
    private fun showDatePicker()
    {
        val newFrament = HireDatePickerFragment()
        newFrament.show(parentFragmentManager,"datepicker")
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

}