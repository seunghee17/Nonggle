package com.example.nongglenonggle.presentation.view.signup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.nongglenonggle.R
import com.example.nongglenonggle.presentation.base.BaseFragment
import com.example.nongglenonggle.databinding.FragmentSignupDBinding
import com.example.nongglenonggle.presentation.view.login.LoginActivity
import com.example.nongglenonggle.presentation.viewModel.signup.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupDFragment : BaseFragment<FragmentSignupDBinding>(R.layout.fragment_signup_d) {
    private val viewModel: SignupViewModel by activityViewModels()
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

        val addressSearch = binding.addressSearch


        addressSearch.setOnClickListener{
            moveToNext()
        }

        viewModel.addressResult.observe(viewLifecycleOwner){
            istrue->
            //선택한 주소 입력됨
            binding.firstaddressTxt.text = viewModel.addressResult.value
            binding.addressSearch.hint=""
        }
        binding.nextBtn.setOnClickListener{
            if(viewModel.dstepActive.value == true)
            {
                moveToEnd()
            }
        }

    }
    fun moveToNext()
    {
        replaceFragment(AddressSearchFragment(), R.id.signup_fragmentcontainer)
    }
    fun moveToEnd(){
        val intent = Intent(context,LoginActivity::class.java)
        startActivity(intent)
    }

}