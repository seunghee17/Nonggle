package com.example.nongglenonggle.view.signup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.nongglenonggle.R
import com.example.nongglenonggle.base.BaseFragment
import com.example.nongglenonggle.databinding.FragmentSignupABinding
import com.example.nongglenonggle.viewModel.signup.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupAFragment : BaseFragment<FragmentSignupABinding>(R.layout.fragment_signup_a) {
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

        //구인자 회원유형 선택시
        binding.farmerType.setOnClickListener{
            viewModel.updateHireType()
            Log.e("tan", "${viewModel.isHire.value}")
            Log.e("tan", "${viewModel.isActiveNext.value}")
        }


        //구직자 회원유형 선택시
        binding.workerType.setOnClickListener{
            viewModel.updateWorkerType()
        }

        val nextbtn=binding.nextBtn
        //다음버튼 누를때 동작을 하는 코드
        nextbtn.setOnClickListener{
            viewModel.isActiveNext.observe(viewLifecycleOwner){
                istrue ->
                moveToNext()
            }
        }

    }

    fun moveToNext()
    {
        replaceFragment(SignupBFragment(), R.id.signup_fragmentcontainer)
    }


}