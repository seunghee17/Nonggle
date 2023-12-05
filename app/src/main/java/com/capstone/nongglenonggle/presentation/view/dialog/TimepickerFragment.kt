package com.capstone.nongglenonggle.presentation.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.capstone.nongglenonggle.databinding.FragmentTimepickerBinding
import com.capstone.nongglenonggle.presentation.viewModel.farmer.FarmerNoticeViewModel
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
        typePicker.maxValue = typePickerValues.size -1
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

        var selectedTypeValue = typePickerValues[typePicker.value]
        var selectedHourValue = hourPicker.value
        var selectedMinuteValue = String.format("%02d", minutePicker.value)

        typePicker.setOnValueChangedListener { _, _, newVal ->
            selectedTypeValue = typePickerValues[newVal]
        }

        hourPicker.setOnValueChangedListener { _, _, newVal ->
            selectedHourValue = newVal
        }

        minutePicker.setOnValueChangedListener { _, _, newVal ->
            selectedMinuteValue = String.format("%02d", newVal)
        }

        binding.confirmbtn.setOnClickListener{
            val currentList = viewModel._TimeList.value?.toMutableList() ?: mutableListOf()
            currentList.addAll(listOf(selectedTypeValue, selectedHourValue.toString(), selectedMinuteValue))
            viewModel._TimeList.postValue(currentList)
            dismiss()
        }

    }

}