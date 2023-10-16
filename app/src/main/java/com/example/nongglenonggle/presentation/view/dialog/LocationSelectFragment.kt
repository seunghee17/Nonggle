package com.example.nongglenonggle.presentation.view.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentLocationSelectBinding
import com.example.nongglenonggle.databinding.FragmentTimepickerBinding
import com.example.nongglenonggle.presentation.viewModel.farmer.FarmerNoticeViewModel
import com.example.nongglenonggle.presentation.viewModel.worker.ResumeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LocationSelectFragment : BottomSheetDialogFragment() {
    private val viewModel: ResumeViewModel by activityViewModels()
    private var _binding : FragmentLocationSelectBinding? =null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocationSelectBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.closebtn.setOnClickListener{
            dismiss()
        }
    }


}