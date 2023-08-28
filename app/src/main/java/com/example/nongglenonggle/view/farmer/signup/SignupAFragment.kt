package com.example.nongglenonggle.view.farmer.signup

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentSignupABinding
import com.example.nongglenonggle.viewModel.SignupAViewModel
import com.example.nongglenonggle.viewModel.SignupViewModel

class SignupAFragment : Fragment() {
    private lateinit var viewModel: SignupAViewModel
    private lateinit var binding: FragmentSignupABinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupABinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(SignupAViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        //구인자 회원유형 선택시
        binding.farmerType.setOnClickListener{
            viewModel.onFrameLayoutClick()
            updatetypeBackground()
            updateTypeBackground(viewModel.isClicked)
        }

        //구직자 회원유형 선택시
        binding.workerType.setOnClickListener{
            viewModel.onFrameLayoutClick()
            updatetypewBackground()
            updateTypeBackground(viewModel.isClicked)
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nextbtn=binding.nextBtn
        //다음버튼 누를때 동작을 하는 코드
        nextbtn.setOnClickListener{
            if(nextbtn.background is ColorDrawable)
            {
                val currentColor = (nextbtn.background as ColorDrawable).color
                if(currentColor == resources.getColor(R.color.m1)){
                    viewModel.onNextbtnClick()
                }
            }
        }
        //viewModel.navigateToframentB livedata관찰하고 있다
        //화면의 생명주기를 따라 변경사항을 감지하고 livedata의 값이 변경될때마다 해당 블록이 실행된다
        viewModel.navigateToframentB.observe(viewLifecycleOwner){
            //livedata의 값을 나타내는 변수
                navigate->
            if(navigate){
                //false로 변경하여 다음번에 livedata값이 변경되었을때 이 블록 실행 안됨 즉 값의 초기화 기능
                viewModel.doneNavigating()
                val fragmentB = SignupBFragment()
                val signupViewModel : SignupViewModel by activityViewModels()
                signupViewModel.navigateTo(fragmentB)
            }
        }
    }

    //구인자 측 box 디자인 업데이트
    private fun updatetypeBackground()
    {
        val drawableres = if(viewModel.isClicked) R.drawable.typebox_active else R.drawable.typebox_unactive
        binding.farmerType.setBackgroundResource(drawableres)
    }
    //구직자 측 box 디자인 업데이트
    private fun updatetypewBackground(){
        val drawableres = if(viewModel.isClicked) R.drawable.typebox_active else R.drawable.typebox_unactive
        binding.workerType.setBackgroundResource(drawableres)
    }
    //현재 선택 상황에 따라 '다음' 버튼 배경 색 업데이트
    private fun updateTypeBackground(isClicked:Boolean)
    {
        val drawables = if(isClicked) resources.getColor(R.color.m1) else resources.getColor(R.color.unactive)
        binding.nextBtn.setBackgroundColor(drawables)
    }

}