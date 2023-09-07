package com.example.nongglenonggle.view.farmer.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentSignupDBinding
import com.example.nongglenonggle.viewModel.farmer.signup.AddressSearchViewModel
import com.example.nongglenonggle.viewModel.farmer.signup.SignupDViewModel
import com.example.nongglenonggle.viewModel.farmer.signup.SignupViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignupDFragment : Fragment() {
    private lateinit var viewModel: SignupDViewModel
    private lateinit var binding: FragmentSignupDBinding
    private lateinit var viewModelAddress : AddressSearchViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupDBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(SignupDViewModel::class.java)
        viewModelAddress = ViewModelProvider(this).get(AddressSearchViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addressSearch = binding.addressSearch
        //addressSearch.isFocusable = false

        addressSearch.setOnClickListener{
            viewModel.onMoveTo()
        }

        val istrue = viewModelAddress.isdata.value
        if(istrue == true)
        {
            val addressData = viewModelAddress.addressData.value
            if(addressData !=null)
                {
                    viewModel.updateData(addressData)
                    viewModel.updateisData()
                    println("성공")
                }
                else{
                    println("실패")
                }
        }

//        viewModelAddress.isdata.observe(viewLifecycleOwner){
//            data->
//            if(data==true)
//            {
//                println("지금부터")
//                val addressData = viewModelAddress.addressData.value
//                if(addressData !=null)
//                {
//                    viewModel.updateData(addressData)
//                    viewModel.updateisData()
//                    println("성공")
//                }
//                else{
//                    println("실패")
//                }
//            }
//        }




        //도로명 주소 화면으로의 전환 옵저빙
        viewModel.navigateToframentaddress.observe(viewLifecycleOwner){
            navigate->
            if(navigate)
            {
                //viewModel.DoneNavigating()
                val fragmentaddress = AddressSearchFragment()
                val signupViewModel : SignupViewModel by activityViewModels()
                signupViewModel.navigateTo(fragmentaddress)
            }
        }

    }



}