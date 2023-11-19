package com.example.nongglenonggle.presentation.view.farmer.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentFarmerMypageBinding
import com.example.nongglenonggle.presentation.base.BaseFragment
import com.example.nongglenonggle.presentation.viewModel.farmer.FarmerMyPageViewModel
import com.example.nongglenonggle.presentation.viewModel.farmer.FarmerSearchViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FarmerMypageFragment : BaseFragment<FragmentFarmerMypageBinding>(R.layout.fragment_farmer_mypage) {
    private val viewModel: FarmerMyPageViewModel by activityViewModels()
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

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
            //화면이동
            val destination = ScoreApplierFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer,destination)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

}