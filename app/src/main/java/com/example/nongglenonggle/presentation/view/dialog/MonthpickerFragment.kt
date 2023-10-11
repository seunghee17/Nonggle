package com.example.nongglenonggle.presentation.view.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentMonthpickerBinding
import com.example.nongglenonggle.presentation.viewModel.worker.ResumeViewModel


class MonthpickerFragment : DialogFragment() {
    private val viewModel : ResumeViewModel by activityViewModels()
    private var _binding:FragmentMonthpickerBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMonthpickerBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cancelbtn.setOnClickListener{
            dismiss()
        }
        val year = binding.yearpicker
        val month = binding.monthpicker

        var minValue = 0
        var maxValue = 0
        var monthMaxValue=0
        var monthMinValue=0

        minValue = 1980
        maxValue = 2023
        monthMinValue = 1
        monthMaxValue = 12

        year.wrapSelectorWheel = false
        month.wrapSelectorWheel=false

        year.minValue=1980
        month.minValue = 1

        year.maxValue=2023
        month.maxValue = 12

        val yearValue = (minValue..maxValue).map{"${it}년"}.toTypedArray()
        year.displayedValues = yearValue

        val MonthValue = (monthMinValue..monthMaxValue).map{"${it}월"}.toTypedArray()
        month.displayedValues = MonthValue

        binding.confirmbtn.setOnClickListener{
            val year = year.value
            val month = month.value
            val currentList = viewModel._selectMonthYear.value ?: emptyList()
            viewModel._selectMonthYear.value = currentList + listOf(year,month)

            dismiss()
        }
        binding.cancelbtn.setOnClickListener{
            dismiss()
        }

    }
}