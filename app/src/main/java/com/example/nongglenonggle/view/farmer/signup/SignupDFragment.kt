package com.example.nongglenonggle.view.farmer.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.nongglenonggle.databinding.FragmentSignupDBinding
import com.example.nongglenonggle.viewModel.farmer.signup.SignupViewModel

class SignupDFragment : Fragment() {
    private val viewModel:SignupViewModel by activityViewModels()

    
    private lateinit var binding: FragmentSignupDBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupDBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addressSearch = binding.addressSearch

        addressSearch.setOnClickListener{
            viewModel.navigateToAddressFragment()
        }

        viewModel.addressResult.observe(viewLifecycleOwner){
            istrue->
            //선택한 주소 입력됨
            binding.firstaddressTxt.text = viewModel.addressResult.value
            binding.addressSearch.hint=""
        }






    }



}