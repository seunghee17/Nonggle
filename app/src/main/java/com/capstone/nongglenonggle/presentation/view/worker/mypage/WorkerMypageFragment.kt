package com.capstone.nongglenonggle.presentation.view.worker.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.databinding.FragmentWorkerMypageBinding
import com.capstone.nongglenonggle.presentation.base.BaseFragment
import com.capstone.nongglenonggle.presentation.viewModel.worker.WorkerSearchViewModel

class WorkerMypageFragment : BaseFragment<FragmentWorkerMypageBinding>(R.layout.fragment_worker_mypage) {
    private val viewModel : WorkerSearchViewModel by activityViewModels()

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

        binding.settingBtn.setOnClickListener{
            findNavController().navigate(R.id.settingFragment)
        }
    }

}