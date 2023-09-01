package com.example.nongglenonggle.view.farmer.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nongglenonggle.databinding.FragmentSignupDBinding
import com.example.nongglenonggle.viewModel.farmer.signup.SignupDViewModel

class SignupDFragment : Fragment() {
    private lateinit var viewModel: SignupDViewModel
    private lateinit var binding: FragmentSignupDBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupDBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(SignupDViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}