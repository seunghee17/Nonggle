package com.capstone.nongglenonggle.presentation.view.farmer.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.capstone.nongglenonggle.presentation.base.BaseFragment
import com.capstone.nongglenonggle.presentation.viewModel.farmer.FarmerMyPageViewModel
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.databinding.FragmentFarmerMypageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FarmerMypageFragment : BaseFragment<FragmentFarmerMypageBinding>(R.layout.fragment_farmer_mypage) {
    private val viewModel: FarmerMyPageViewModel by activityViewModels()
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    val useruid= firebaseAuth.currentUser?.uid

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
        binding.lifecycleOwner = viewLifecycleOwner

        if (useruid != null) {
            viewModel.getSuggestionData()
        }

        binding.score.setOnClickListener{
            findNavController().navigate(R.id.scoreApplierFragment)
        }
        binding.settingBtn.setOnClickListener{
            findNavController().navigate(R.id.settingFragment2)
        }
    }


}