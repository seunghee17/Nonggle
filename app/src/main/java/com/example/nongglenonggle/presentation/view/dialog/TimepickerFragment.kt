package com.example.nongglenonggle.presentation.view.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import androidx.core.view.size
import androidx.fragment.app.activityViewModels
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentTimepickerBinding
import com.example.nongglenonggle.presentation.viewModel.farmer.FarmerNoticeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TimepickerFragment : BottomSheetDialogFragment() {
    private val viewModel: FarmerNoticeViewModel by activityViewModels()
    private var _binding : FragmentTimepickerBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTimepickerBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.closebtn.setOnClickListener{
            dismiss()
        }
        val typePicker = binding.typepicker
        val hourPicker = binding.hourpicker
        val minutePicker = binding.minutepicker
        val typePickerValues = arrayOf("AM","PM")


        typePicker.minValue = 0
        typePicker.maxValue = typePicker.size -1
        typePicker.displayedValues = typePickerValues

        val hourMinValue =1
        val hourMaxValue = 12
        val minuteMinValue = 0
        val minuteMaxValue = 59

        hourPicker.minValue = hourMinValue
        hourPicker.maxValue = hourMaxValue

        minutePicker.minValue = minuteMinValue
        minutePicker.maxValue = minuteMaxValue
        minutePicker.setFormatter{value->
            String.format("%02d", value)
        }
        //옵션무한 반복 금지
        typePicker.wrapSelectorWheel=false
        hourPicker.wrapSelectorWheel=false
        minutePicker.wrapSelectorWheel=false

        val selectedTypeValue = typePickerValues[typePicker.value]
        val selectedHourValue = hourPicker.value
        val seledtedMinuteValue = String.format("%02d", minutePicker.value)

        binding.confirmbtn.setOnClickListener{
            val currentList = viewModel._TimeList.value ?: emptyList()
            viewModel._TimeList.value = currentList + listOf(selectedTypeValue,selectedHourValue.toString(),seledtedMinuteValue)
            dismiss()
        }

    }

}