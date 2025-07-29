package com.capstone.nongglenonggle.presentation.view.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.presentation.base.BaseFragment
import com.capstone.nongglenonggle.databinding.FragmentSignupABinding
import com.capstone.nongglenonggle.presentation.viewModel.signup.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint
//Ui요소 정의
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
        binding.composeView.setContent {
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SelectTypeDescription()
                Spacer(modifier = Modifier.height(10.dp))
                userTypeContainer(
                    UserType.MANAGER,
                    modifier = Modifier.padding(horizontal = 20.dp),
                    onClick = {},
                    selectType = UserType.WORKER
                )
                Spacer(modifier = Modifier.height(16.dp))
                userTypeContainer(
                    UserType.WORKER,
                    modifier = Modifier.padding(horizontal = 20.dp),
                    onClick = {},
                    selectType = UserType.WORKER
                )
                Spacer(modifier = Modifier.weight(1f))
                nextBtn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    enable = true,
                    onClick = { goToNextScreen() })
            }
        }

        //구인자 회원유형 선택시
//        binding.farmerType.setOnClickListener{
//            viewModel.updateHireType()
//        }
//
//
//        //구직자 회원유형 선택시
//        binding.workerType.setOnClickListener{
//            viewModel.updateWorkerType()
//        }

    }

    fun goToNextScreen() {
        findNavController().navigate(R.id.action_signupAFragment_to_signupBFragment)
    }

}