package com.example.nongglenonggle.presentation.view.dialog

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentCareerAddBinding
import com.example.nongglenonggle.presentation.util.hideClearButton
import com.example.nongglenonggle.presentation.util.showClearButton
import com.example.nongglenonggle.presentation.view.adapter.SpinnerAdapter
import com.example.nongglenonggle.presentation.viewModel.worker.ResumeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.coroutineScope


class CareerAddFragment : BottomSheetDialogFragment(){
    private val viewModel:  ResumeViewModel by activityViewModels()
    private var _binding:FragmentCareerAddBinding? =null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCareerAddBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.closebtn.setOnClickListener{
            dismiss()
        }
        //미만
        binding.monthSelectA.setOnClickListener{
            viewModel.activeA()
            viewModel._selectMonthYear.postValue(emptyList())
        }
        binding.monthSelectB.setOnClickListener{
            viewModel.activeB()
            viewModel._selectMonthYear.postValue(emptyList())
        }

        binding.careerEdit.setOnFocusChangeListener{view,isfocus->
            viewModel.activeCareer(isfocus)
            Log.e("career","${viewModel.activeCareerEdit.value}")
            if(isfocus && binding.careerEdit.text?.isNotEmpty()==true){
                binding.careerEdit.showClearButton(R.drawable.xcircle)
            }else{
                binding.careerEdit.hideClearButton()
            }
        }
        binding.careerEdit.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(binding.careerEdit.isFocused && s?.isNotEmpty() == true){
                    binding.careerEdit.showClearButton(R.drawable.xcircle)
                }else{
                    binding.careerEdit.hideClearButton()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.activeCareerEdit.observe(viewLifecycleOwner){isfocus->
                    if(isfocus && binding.careerEdit.text != null){
                        binding.careerEdit.showClearButton(R.drawable.xcircle)
                    }
                    else{
                        binding.careerEdit.hideClearButton()
                    }
                }
                viewModel.careerTitle = s.toString()
            }
        })
        initSpinner(binding.spinner , R.array.select_month_day, "근무 일 선택",viewModel._activeSpinner)


        binding.selectCalendar.setOnTouchListener{v,event->
            if(event.action == MotionEvent.ACTION_UP){
                //다이얼로그 띄우기
                val dialog = MonthpickerFragment()
                dialog.show(parentFragmentManager, "MonthpickerFragmentTag")
            }
            false
        }

        viewModel.selectMonthYear.observe(viewLifecycleOwner){data->
            if(viewModel.selectMonthYear.value?.size == 2){
                data?.let{
                    viewModel._activeMonthYear.postValue(true)
                    binding.monthTxt.text = "${it.get(0)}년 ${it.get(1)}월"
                    if(viewModel.activeMonthYearA.value == true){
                        binding.monthTxtA.text = "${it.get(0)}년 ${it.get(1)}월"
                    }
                }
            }
            else if(viewModel.selectMonthYear.value?.size == 4){
                //1개월 이상 선택한 경우
                data?.let{
                    viewModel._activeMonthYearB.postValue(true)
                    binding.monthTxtB.text = "${it.get(2)}년 ${it.get(3)}월"
                }
            }
        }

        binding.selectCalendarA.setOnTouchListener{v,event->
            if(event.action == MotionEvent.ACTION_UP){
                val dialog = MonthpickerFragment()
                dialog.show(parentFragmentManager, "MonthpickerFragmentTag")
                viewModel._activeMonthYearA.postValue(true)
            }
            false
        }
        binding.selectCalendarB.setOnTouchListener{v,event->
            if(event.action == MotionEvent.ACTION_UP){
                val dialog = MonthpickerFragment()
                dialog.show(parentFragmentManager, "MonthpickerFragmentTag")
            }
            false
        }
        binding.careerDetail.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setAddActive()
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.careerDetail = s.toString()
            }

        })

        binding.plusBtn.setOnClickListener{
            if(viewModel.activePlus.value == false){
                Toast.makeText(requireContext(),"입력사항을 모두 채워주세요!",Toast.LENGTH_SHORT).show()
            }
            else{
                //코루틴으로 데이터 저장후 dismiss
                viewModel.setResumeSummary()
                dismiss()
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
                viewModel.getSpinnerValue = items[p2]
                p1?.isPressed = false
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                viewModelLiveData.postValue(false)
            }
        }
    }



}