package com.example.nongglenonggle.view.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.nongglenonggle.R
import com.example.nongglenonggle.base.BaseFragment
import com.example.nongglenonggle.databinding.FragmentSignupBBinding
import com.example.nongglenonggle.viewModel.signup.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupBFragment : BaseFragment<FragmentSignupBBinding>(R.layout.fragment_signup_b) {
    private lateinit var viewModel: SignupViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        val view= super.onCreateView(inflater, container, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignupViewModel::class.java)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //edittext들 재정의
        val edittext1 = binding.name
        val edittext2 = binding.phnum
        val edittext3 = binding.verifyid
        val edittext4 = binding.passwordbox1
        val edittext5 = binding.passwordbox2
        val clearbtn1 = binding.clearbtn1
        val clearbtn2 = binding.clearbtn2
        val clearbtn3 = binding.clearbtn3
        val clearbtn4 = binding.clearbtn4
        val clearbtn5 = binding.clearbtn5
        val nextbtn = binding.nextBtn

        val edittextArray : Array<EditText> = arrayOf(edittext1,edittext2,edittext3,edittext4,edittext5)
        val clearbutton: Array<ImageView> = arrayOf(clearbtn1,clearbtn2,clearbtn3,clearbtn4,clearbtn5)

        //각 텍스트필드의 clearbtn기능 구현
        for(i in 0..4)
        {
            //삭제버튼 누를때 동작 구현
            clearbutton[i].setOnClickListener{
                edittextArray[i].setText("");
            }
        }
        //edittext1 -> 이름 입력란 포커싱 이벤트
        edittext1.setOnFocusChangeListener{
                view,isFocus->
            viewModel._isFocusName.postValue(true)
        }
        //edittext2-> 전화번호 입력 오류 감지 색상 업데이트
        edittext2.setOnFocusChangeListener{
                view,isFocus ->
            viewModel._isFocusId.postValue(true)
        }
        edittext2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().contains("-"))
                {
                    viewModel._isIdWrong.postValue(true)
                }
                else
                {
                    viewModel._isIdWrong.postValue(false)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        //edittext3 -> 인증번호 필드 포커싱
        edittext3.setOnFocusChangeListener{
                view,isFocus ->
            viewModel._isFocusVerification.postValue(true)
        }


        //인증번호 전송을 위해 버튼 누를시 오류 텍스트 visile용
        binding.sendnum.setOnClickListener{
            val phonenum = "+821002020202"
            //val callbacks = createVerificationCallbacks()
            //viewModel.startPhoneNumberVerificcation(phonenum, callbacks)
            viewModel.viewModelScope.launch(Dispatchers.IO) {
                viewModel.startPhoneNumberVerification(phonenum)

            }
        }


        binding.confirmBtn.setOnClickListener {
            //viewModel.signInWithPhoneAuthCredential(binding.verifyid.text.toString())
            viewModel.viewModelScope.launch {
                viewModel.signInWithPhoneAuthCredential(binding.verifyid.text.toString())
            }
        }
        //필요한가?->필요없을듯
//        viewModel.authcomplete.observe(viewLifecycleOwner){
//            isAuthComplete->
//            if(!isAuthComplete){
//                updateErrorColorAuth(edittext3)
//            }else{
//                //Toast.makeText(getActivity(), "인증성공", Toast.LENGTH_LONG).show()
//                updateSuccessColorAuth(edittext3)
//            }
//        }

        edittext4.setOnFocusChangeListener{
                view,isFocus ->
            viewModel._isFocusPW.postValue(true)
        }

        //비밀번호 입력형식 오류 판단
        edittext4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                for(s in p0.toString())
                {
                    if(s !in 'A'..'Z' && s !in 'a'..'z' && s !in '0'..'9'){
                        viewModel._isPWWrong.postValue(true)
                    }
                    else{
                        viewModel._isPWWrong.postValue(false)
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        edittext5.setOnFocusChangeListener{
                view,isFocus->
            viewModel._isFocusPW2.postValue(true)
            if(isFocus == false)
            {
                viewModel._isFocusPW2.postValue(false)
            }
        }

        edittext5.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString() == edittext4.text.toString())
                {
                    viewModel._isPWSame.postValue(true)
                }
                else
                {
                    viewModel._isPWSame.postValue(false)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        //다음버튼 동작 구현
        nextbtn.setOnClickListener{
            if(viewModel.isPWSame.value == true){
                val phoneNumber = binding.phnum.text.toString()
                val phoneNumberFinal = "$phoneNumber@example.com"
                val passWord = binding.passwordbox2.text.toString()
                viewModel.viewModelScope.launch(Dispatchers.IO) {
                    viewModel.signUpWithEmailPasswordAndPhoneNumber(phoneNumberFinal,passWord,edittext1.text.toString())
                }
                moveToNext()
            }
        }

    }


    fun moveToNext()
    {
        replaceFragment(SignupCFragment(), R.id.signup_fragmentcontainer)
    }


}