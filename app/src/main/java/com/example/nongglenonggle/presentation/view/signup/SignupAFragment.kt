package com.example.nongglenonggle.presentation.view.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.nongglenonggle.R
import com.example.nongglenonggle.presentation.base.BaseFragment
import com.example.nongglenonggle.databinding.FragmentSignupABinding
import com.example.nongglenonggle.presentation.viewModel.signup.SignupViewModel
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
        binding.viewModel = viewModel

        //구인자 회원유형 선택시
        binding.farmerType.setOnClickListener{
            viewModel.updateHireType()
        }


        //구직자 회원유형 선택시
        binding.workerType.setOnClickListener{
            viewModel.updateWorkerType()
        }

        val nextbtn=binding.nextBtn
        //다음버튼 누를때 동작을 하는 코드
        nextbtn.setOnClickListener{
            moveToNext()
        }

    }

    fun moveToNext()
    {
        replaceFragment(SignupBFragment(), R.id.signup_fragmentcontainer)
    }


}