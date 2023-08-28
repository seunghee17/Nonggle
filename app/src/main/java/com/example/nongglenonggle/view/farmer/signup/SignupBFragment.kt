package com.example.nongglenonggle.view.farmer.signup

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentSignupBBinding
import com.example.nongglenonggle.viewModel.SignupBViewModel
import com.example.nongglenonggle.viewModel.SignupViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class SignupBFragment : Fragment() {
    private lateinit var viewModel: SignupBViewModel
    private lateinit var binding: FragmentSignupBBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        binding = FragmentSignupBBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(SignupBViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

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
            viewModel.setboxFocus(isFocus)
            updateFocusColor(edittext1)
            clearbtn1.visibility = if(viewModel.isFocus.value==true) View.VISIBLE else View.INVISIBLE
        }
        //edittext2-> 전화번호 입력 오류 감지 색상 업데이트
        edittext2.setOnFocusChangeListener{
            view,isFocus ->
            viewModel.setboxFocus(isFocus)
            updateFocusColor(edittext2)
            clearbtn2.visibility = if(viewModel.isFocus.value==true) View.VISIBLE else View.INVISIBLE
        }
        edittext2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.updateErrorStatus(p0.toString())
                viewModel.updateButtonStatus(p0.toString())
                updateErrorColor(edittext2)
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        //edittext3 -> 인증번호 필드 포커싱
        edittext3.setOnFocusChangeListener{
            view,isFocus ->
            viewModel.setboxFocus(isFocus)
            updateFocusColor(edittext3)
            clearbtn3.visibility = if(viewModel.isFocus.value == true) View.VISIBLE else View.INVISIBLE
        }

        //edittext3-> 버튼색상 변경을 위해
        edittext3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.updateconfirmbtnStatus(p0.toString())

            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        //인증번호 전송을 위해 버튼 누를시 viewmodel의 해당함수 호출
        binding.sendnum.setOnClickListener{
            val phonenum = "+821002020202"
            val callbacks = createVerificationCallbacks()
            viewModel.startPhoneNumberVerificcation(phonenum, callbacks)
        }

        binding.confirmBtn.setOnClickListener {
            viewModel.signInWithPhoneAuthCredential(binding.verifyid.text.toString())
        }
        viewModel.authcomplete.observe(viewLifecycleOwner){
            isAuthComplete->
            if(!isAuthComplete){
                updateErrorColorAuth(edittext3)
            }else{
                Toast.makeText(getActivity(), "인증성공", Toast.LENGTH_LONG).show()
                updateSuccessColorAuth(edittext3)
            }
        }
        
        edittext4.setOnFocusChangeListener { view, isFocus ->
            viewModel.setboxFocus(isFocus)
            updateFocusColor(edittext4)
            clearbtn4.visibility = if(viewModel.isFocus.value==true) View.VISIBLE else View.INVISIBLE
        }
        edittext4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.updatePwErrorStatus(p0.toString())
                updatePWErrorColor(edittext4)
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        edittext5.setOnFocusChangeListener{
            view,isFocus->
            viewModel.setboxFocus(isFocus)
            updateFocusColor(edittext5)
            clearbtn5.visibility = if(viewModel.isFocus.value==true) View.VISIBLE else View.INVISIBLE
        }
        edittext5.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.updatePwErrorSame(edittext4.text.toString(),p0.toString())
                updatePWErrorSameColor(edittext5)
                viewModel.onNextClick()
                updateTypeBackground(viewModel.isClicked)
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //다음 버튼 binding
        val phnum = binding.phnum
        val sendnum = binding.sendnum
        val verifyid = binding.verifyid
        val confirm = binding.confirmBtn
        val nextbtn=binding.nextBtn
        //다음버튼 누를때 화면전환 이벤트
        nextbtn.setOnClickListener{
            if(nextbtn.background is ColorDrawable)
            {
                val currentColor = (nextbtn.background as ColorDrawable).color
                if(currentColor==resources.getColor(R.color.m1))
                {
                    viewModel.onNextbtnClick()
                }
            }
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
    private fun createVerificationCallbacks(): PhoneAuthProvider.OnVerificationStateChangedCallbacks
    {
        return object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                //인증 자동으로 완료된 후
                //Toast.makeText(getActivity(), "인증성공", Toast.LENGTH_LONG).show()
                viewModel._authcomplete.value = true
            }

            override fun onVerificationFailed(e: FirebaseException) {
                viewModel._authcomplete.value = false
                //updateErrorColor(binding.verifyid)
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                viewModel.verificationId = verificationId
            }
        }
    }
    fun updateFocusColor(EditText: EditText){
        val activeLine = if(viewModel.isFocus.value == true) R.color.m1 else R.color.g_line
        EditText.backgroundTintList=resources.getColorStateList(activeLine)
    }
    fun updateErrorColor(EditText: EditText)
    {
        val errorLine= if(viewModel.isErrorline.value==true) R.color.error else R.color.m1
        EditText.backgroundTintList = resources.getColorStateList(errorLine)
    }
    fun updateErrorColorAuth(EditText: EditText)
    {
        //val errorLine = if(viewModel.authcomplete.value ==false) R.color.error else R.color.m1
        val errorLine = R.color.error
        EditText.backgroundTintList =resources.getColorStateList(errorLine)
    }
    fun updateSuccessColorAuth(EditText: EditText)
    {
        //val successLine = if(viewModel.authcomplete.value==true) R.color.m1 else R.color.error
        //EditText.backgroundTintList = resources.getColorStateList(successLine)
        val successLine = R.color.m1
        EditText.backgroundTintList = resources.getColorStateList(successLine)
    }
    //첫번째 비밀번호 에러 판단
    fun updatePWErrorColor(EditText: EditText)
    {
        val errorLine= if(viewModel.ispasswordLine1.value==true) R.color.m1 else R.color.error
        EditText.backgroundTintList = resources.getColorStateList(errorLine)
    }
    //두번째 비밀번호 에러 판단
    fun updatePWErrorSameColor(EditText: EditText)
    {
        val errorLine= if(viewModel.ispasswordLine2.value==true) R.color.m1 else R.color.error
        EditText.backgroundTintList = resources.getColorStateList(errorLine)
    }
    //다음버튼 배경색
    private fun updateTypeBackground(isClicked:Boolean)
    {
        val drawables = if(isClicked) resources.getColor(R.color.m1) else resources.getColor(R.color.unactive)
        binding.nextBtn.setBackgroundColor(drawables)
    }


}