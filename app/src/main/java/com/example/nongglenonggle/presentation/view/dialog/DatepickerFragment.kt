package com.example.nongglenonggle.presentation.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.nongglenonggle.databinding.FragmentDatepickerBinding
import com.example.nongglenonggle.presentation.viewModel.farmer.FarmerNoticeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class DatepickerFragment : BottomSheetDialogFragment() {
    private val viewModel: FarmerNoticeViewModel by activityViewModels()
    private var _binding : FragmentDatepickerBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDatepickerBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.closebtn.setOnClickListener{
            dismiss()
        }

        val year = binding.yearpicker
        val month = binding.monthpicker
        val day = binding.daypicker

        val minValue = 2022
        val maxValue = 2033
        val monthMinValue = 1
        val monthMaxValue = 12
        val DayMinValue = 1
        val DayMaxValue=31

        year.wrapSelectorWheel = false
        month.wrapSelectorWheel=false
        day.wrapSelectorWheel=false

        year.minValue=2023
        month.minValue = 1
        day.minValue = 1

        year.maxValue=2033
        month.maxValue = 12
        day.maxValue = 31

        val yearValue = (minValue..maxValue).map{"${it}년"}.toTypedArray()
        year.displayedValues = yearValue

        val MonthValue = (monthMinValue..monthMaxValue).map{"${it}월"}.toTypedArray()
        month.displayedValues = MonthValue

        val DateValue = (DayMinValue..DayMaxValue).map{"${it}일"}.toTypedArray()
        day.displayedValues = DateValue


        binding.confirmbtn.setOnClickListener{
            val year = year.value
            val month = month.value
            val day = day.value

            val currentList = viewModel._DateList.value ?: emptyList()
            viewModel._DateList.value = currentList + listOf(year,month,day)

            dismiss()
        }
    }



}