package com.example.nongglenonggle.presentation.view.farmer.notice

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.ViewPager2
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentNoticeCBinding
import com.example.nongglenonggle.presentation.base.BaseFragment
import com.example.nongglenonggle.presentation.view.adapter.SpinnerAdapter
import com.example.nongglenonggle.presentation.viewModel.farmer.FarmerNoticeViewModel
import com.google.android.play.core.integrity.v
import org.w3c.dom.Text

class noticeCFragment : BaseFragment<FragmentNoticeCBinding>(R.layout.fragment_notice_c) {
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

        //필요인원
        binding.q1editTxt.setOnFocusChangeListener{
            view,isFocus ->
            viewModel._requiredPeople.postValue(true)
        }

        binding.q1editTxt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.q1editTxt.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.xcircle,0)
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.requiredPeople.observe(viewLifecycleOwner){isfocus->
                    if(isfocus && binding.q1editTxt.text != null){
                        binding.q1editTxt.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.xcircle,0)
                        binding.q1editTxt.getClearButton(R.drawable.xcircle)
                    }else{
                        binding.q1editTxt.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
                    }
                }
            }

        })


// 이 함수를 이용하여 스피너 초기화
        initSpinner(binding.startAgeSpinner, R.array.start_select_age, "시작연령대", viewModel._activeStartAge)
        initSpinner(binding.endAgeSpinner, R.array.end_select_age, "끝연령대", viewModel._activeEndAge)



        //성별선택부분
        binding.women.setOnClickListener{
            viewModel._activeWomen.value = true
            viewModel._activemen.value = false
            viewModel._activeBoth.value = false
            viewModel._noticeGender.value = "여자"
        }
        binding.man.setOnClickListener{
            viewModel._activeWomen.value = false
            viewModel._activemen.value = true
            viewModel._activeBoth.value = false
            viewModel._noticeGender.value = "남자"
        }
        binding.both.setOnClickListener{
            viewModel._activeWomen.value = false
            viewModel._activemen.value = false
            viewModel._activeBoth.value = true
            viewModel._noticeGender.value = "성별무관"
        }

        //일급 active될때 경우
        binding.dayMoney.setOnClickListener{
            viewModel._activedayMoney.value = true
            viewModel._activeTimeMoney.value = false
            viewModel._activeCounsel.value = false
            viewModel._noticeMoney.value = binding.dayMoneyTxt.text.toString()
        }
        //시급
        binding.timeMoney.setOnClickListener{
            viewModel._activedayMoney.value = false
            viewModel._activeTimeMoney.value = true
            viewModel._activeCounsel.value = false
            viewModel._noticeMoney.value = binding.timeMoneyTxt.text.toString()

        }
        //급여협의
        binding.counselMoney.setOnClickListener{
            viewModel._activedayMoney.value = false
            viewModel._activeTimeMoney.value = false
            viewModel._activeCounsel.value = true
            viewModel._noticeMoney.value = binding.counselMoneyTxt.text.toString()

        }

        binding.moneyTxt.setOnFocusChangeListener{
            view,isFocus->
            viewModel._activeEdittxt.postValue(isFocus)
        }

        binding.moneyTxt.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.moneyTxt.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.xcircle,0)
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.activeEdittxt.observe(viewLifecycleOwner){isfocus->
                    if(isfocus && binding.moneyTxt.text != null){
                        binding.moneyTxt.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.xcircle,0)
                        binding.moneyTxt.getClearButton(R.drawable.xcircle)
                    }
                    else{
                        binding.moneyTxt.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
                    }
                }
                viewModel._payMoney.postValue("${p0.toString()}원")
            }
        })

        binding.workType1.setOnClickListener{
            viewModel._activeWorkType1.value = true
            viewModel._activeWorkType2.value = false
            viewModel._activeWorkType3.value = false
            viewModel._workType.postValue("체류형")
        }
        binding.workType2.setOnClickListener{
            viewModel._activeWorkType1.value = false
            viewModel._activeWorkType2.value = true
            viewModel._activeWorkType3.value = false
            viewModel._workType.postValue("출퇴근형")
        }
        binding.workType3.setOnClickListener{
            viewModel._activeWorkType1.value = false
            viewModel._activeWorkType2.value = false
            viewModel._activeWorkType3.value = true
            viewModel._workType.postValue("계약직")
        }

        binding.applyType1.setOnClickListener{
            viewModel._applyType1.postValue(true)
            viewModel._applyType2.postValue(false)
            viewModel._applyType3.postValue(false)
            viewModel._applyType.postValue("비경험자")
        }
        binding.applyType2.setOnClickListener{
            viewModel._applyType1.postValue(false)
            viewModel._applyType2.postValue(true)
            viewModel._applyType3.postValue(false)
            viewModel._applyType.postValue("경험자")
        }
        binding.applyType3.setOnClickListener{
            viewModel._applyType1.postValue(false)
            viewModel._applyType2.postValue(false)
            viewModel._applyType3.postValue(true)
            viewModel._applyType.postValue("경력무관")
        }
        binding.certificationNeed.setOnClickListener{
            viewModel._activeCertifi1.postValue(true)
            viewModel._activeCertifi2.postValue(false)
            //이경우 object로 데이터베이스에 집어넣기
            //추후 재정의하기!!!
        }
        binding.certificationUnneed.setOnClickListener{
            viewModel._activeCertifi1.postValue(false)
            viewModel._activeCertifi2.postValue(true)
            //처리방법고민하기!!
        }

        binding.certificationInfo.setOnFocusChangeListener{view,isFocus->
            viewModel._active_Edittxt.postValue(isFocus)
        }
        binding.certificationInfo.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.certificationInfo.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.xcircle,0)
                viewModel._active_confirm.postValue(true)
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.active_Edittxt.observe(viewLifecycleOwner){isfocus->
                    if(isfocus && binding.certificationInfo.text != null){
                        binding.certificationInfo.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.xcircle,0)
                        binding.certificationInfo.getClearButton(R.drawable.xcircle)
                    }
                    else{
                        binding.certificationInfo.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
                    }
                    viewModel._active_confirm.postValue(true)
                }
            }

        })
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
    // 스피너 어댑터 초기화 함수
    private fun initSpinner(spinner: Spinner, itemsArrayId: Int, hintText: String, viewModelLiveData: MutableLiveData<Boolean>) {
        val items = resources.getStringArray(itemsArrayId)
        val adapter = SpinnerAdapter(requireContext(), R.layout.item_spinner, items, R.id.list_content)
        adapter.setHintTextColor(hintText, R.color.g3)
        spinner.adapter = adapter
        spinner.setSelection(adapter.count)

        spinner.post {
            spinner.dropDownVerticalOffset = spinner.height
        }

        spinner.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                viewModelLiveData.postValue(true)
            }
            false
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModelLiveData.postValue(false)
                p1?.isPressed = false
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                viewModelLiveData.postValue(false)
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

}