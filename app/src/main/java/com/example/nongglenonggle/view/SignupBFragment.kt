package com.example.nongglenonggle.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentSignupBBinding
import com.example.nongglenonggle.viewModel.SignupBViewModel
import com.example.nongglenonggle.viewModel.SignupViewModel

class SignupBFragment : Fragment() {
    private lateinit var viewModel:SignupBViewModel
    private lateinit var binding:FragmentSignupBBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        binding = FragmentSignupBBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(SignupBViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        //edittext들 재정의
        val edittext1 = binding.name
        val edittext2 = binding.phnum
        val edittext3 = binding.verify
        val edittext4 = binding.passwordbox1
        val edittext5 = binding.passwordbox2
        val clearbtn1 = binding.clearbtn1
        val clearbtn2 = binding.clearbtn2
        val clearbtn3 = binding.clearbtn3
        val clearbtn4 = binding.clearbtn4
        val clearbtn5 = binding.clearbtn5

        val edittextArray : Array<EditText> = arrayOf(edittext1,edittext2,edittext3,edittext4,edittext5)
        val clearbutton: Array<ImageView> = arrayOf(clearbtn1,clearbtn2,clearbtn3,clearbtn4,clearbtn5)

        //edittext의 ui업데이트구현
        for(i in 0..4)
        {
            edittextArray[i].setOnFocusChangeListener{view, isFocus ->
                viewModel.setboxFocus()
                updatelineColor()
                clearbutton[i].setVisibility(View.VISIBLE)
            }
            //삭제버튼 누를때 동작 구현
            clearbutton[i].setOnClickListener{
                edittextArray[i].setText("");
            }
        }




        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nextbtn=binding.nextBtn
        //다음버튼 누를때 화면전환 이벤트
        nextbtn.setOnClickListener{
            viewModel.onNextbtnClick()
        }
        viewModel.navigateToframentC.observe(viewLifecycleOwner){
                navigate->
            if(navigate){
                viewModel.doneNavigating()
                val fragmentC = SignupCFragment()
                val signupViewModel : SignupViewModel by activityViewModels()
                signupViewModel.navigateTo(fragmentC)
            }
        }
    }

    private fun updatelineColor()
     {
        val activeLine= if(viewModel.isFocus) R.color.m1 else R.color.g_line
         binding.name.backgroundTintList = resources.getColorStateList(activeLine)
    }
}