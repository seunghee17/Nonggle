package com.example.nongglenonggle.presentation.view.dialog

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentFilterBottomSheetBinding
import com.example.nongglenonggle.presentation.viewModel.farmer.FarmerSearchViewModel
import com.example.nongglenonggle.presentation.viewModel.worker.WorkerSearchViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterBottomSheetFragment : BottomSheetDialogFragment() {
    private val bottomSheetVM : WorkerSearchViewModel by activityViewModels()
    private val bottomSheetFarmerVM: FarmerSearchViewModel by activityViewModels()
    private var _binding:FragmentFilterBottomSheetBinding? =null
        private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterBottomSheetBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomSheetVM = bottomSheetVM
        binding.one.setOnClickListener{
            bottomSheetVM.getNoticeBasedOntype("체류형")
            bottomSheetFarmerVM._workType.postValue("체류형")
            dismiss()
        }
        binding.two.setOnClickListener{
            bottomSheetVM.getNoticeBasedOntype("출퇴근형")
            bottomSheetFarmerVM._workType.postValue("출퇴근형")
            dismiss()
        }
    }

}