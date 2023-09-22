package com.example.nongglenonggle.view.farmer.notice

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentDatepickerBinding
import com.example.nongglenonggle.databinding.FragmentNoticeBBinding
import com.example.nongglenonggle.viewModel.farmer.notice.FarmerNoticeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar


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
        binding.confirmbtn.setOnClickListener{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            viewModel.DateList.add(year)
            viewModel.DateList.add(month)
            viewModel.DateList.add(day)
        }
    }


}