package com.example.nongglenonggle.presentation.view.farmer.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentFarmerMypageBinding
import com.example.nongglenonggle.presentation.base.BaseFragment
import com.example.nongglenonggle.presentation.viewModel.farmer.FarmerSearchViewModel

class FarmerMypageFragment : BaseFragment<FragmentFarmerMypageBinding>(R.layout.fragment_farmer_mypage) {
    private val viewModel: FarmerSearchViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        binding.scoring.setOnClickListener{
            //화면이동
        }
    }

}