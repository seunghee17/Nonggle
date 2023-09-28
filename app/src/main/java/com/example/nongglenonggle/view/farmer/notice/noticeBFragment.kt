package com.example.nongglenonggle.view.farmer.notice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentNoticeBBinding
import com.example.nongglenonggle.view.adapter.SpinnerAdapter
import com.example.nongglenonggle.viewModel.farmer.FarmerNoticeViewModel

class noticeBFragment : Fragment() {
    private var _binding : FragmentNoticeBBinding? = null
    private val viewModel: FarmerNoticeViewModel by activityViewModels()
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoticeBBinding.inflate(inflater,container,false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            viewModel.changeToActive()
        }

        binding.titleEdit.setOnTouchListener { view, event ->
            if(event.action == MotionEvent.ACTION_UP){
                val drawableRightXStart = binding.titleEdit.right - binding.titleEdit.totalPaddingRight
                if(event.rawX >= drawableRightXStart)
                {
                    binding.titleEdit.setText("")
                    return@setOnTouchListener true
                }
            }
            false
        }

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
                Log.e("sagf", "ㄴㅇㅎ")
                showDatePicker()
                if(viewModel.DateList.isNotEmpty())
                {
                    binding.datepicker1.setText("${viewModel.DateList[0]}년 ${viewModel.DateList[1]}월 ${viewModel.DateList[2]}일")
                    viewModel.DateList.clear()
                }
            }
            false
        }

        binding.datepicker2.setOnClickListener{
            showDatePicker()
            binding.datepicker2.setText("${viewModel.DateList[0]}년 ${viewModel.DateList[1]}월 ${viewModel.DateList[2]}일")
            viewModel.DateList.clear()
        }

    }

    private fun showDatePicker()
    {
        val newFrament = DatepickerFragment()
        newFrament.show(parentFragmentManager,"datepicker")
        Log.e("sagf", "이거출력")
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}