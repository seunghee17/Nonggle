package com.example.nongglenonggle.presentation.view.farmer.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentFarmerHomeBinding
import com.example.nongglenonggle.presentation.base.BaseFragment
import com.example.nongglenonggle.presentation.view.login.LoginActivity
import com.example.nongglenonggle.presentation.viewModel.farmer.FarmerHomeViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FarmerhomeFragment : BaseFragment<FragmentFarmerHomeBinding>(R.layout.fragment_farmer_home) {
    private val viewModel: FarmerHomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchUserInfo()
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

        binding.logo.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }
    }

}