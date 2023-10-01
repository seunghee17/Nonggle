package com.example.nongglenonggle.view.farmer.notice

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.nongglenonggle.R
import com.example.nongglenonggle.base.BaseFragment
import com.example.nongglenonggle.databinding.FragmentNoticeBBinding
import com.example.nongglenonggle.view.adapter.SpinnerAdapter
import com.example.nongglenonggle.viewModel.farmer.FarmerNoticeViewModel

class noticeBFragment : BaseFragment<FragmentNoticeBBinding>(R.layout.fragment_notice_b) {
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


}