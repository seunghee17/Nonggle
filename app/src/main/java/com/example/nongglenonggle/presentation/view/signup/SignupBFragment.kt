package com.example.nongglenonggle.presentation.view.signup

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import com.example.nongglenonggle.R
import com.example.nongglenonggle.presentation.base.BaseFragment
import com.example.nongglenonggle.databinding.FragmentSignupBBinding
import com.example.nongglenonggle.presentation.viewModel.signup.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupBFragment : BaseFragment<FragmentSignupBBinding>(R.layout.fragment_signup_b) {
    private val viewModel: SignupViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        val view= super.onCreateView(inflater, container, savedInstanceState)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        val nextbtn = binding.nextBtn
        var phonenum = ""

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
            viewModel._isFocusName.postValue(isFocus)
        }
        //edittext2-> 전화번호 입력 오류 감지 색상 업데이트
        edittext2.setOnFocusChangeListener{
                view,isFocus ->
            viewModel._isFocusId.postValue(isFocus)
            viewModel._IdBtnActive.value = true
        }
        edittext2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().contains("-")) {
                    viewModel._isIdWrong.postValue(true)
                    val colorStateList = ColorStateList.valueOf(resources.getColor(R.color.error))
                    edittext2.backgroundTintList = colorStateList
                }
                else {
                    viewModel._isIdWrong.postValue(false)
                    val colorStateList = ColorStateList.valueOf(resources.getColor(R.color.m1))
                    edittext2.backgroundTintList = colorStateList
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                phonenum = p0.toString()
            }
        })

        //edittext3 -> 인증번호 필드 포커싱
        edittext3.setOnFocusChangeListener{
                view,isFocus ->
            viewModel._isFocusVerification.postValue(isFocus)
        }


        //인증번호 전송을 위해 버튼 누를시 오류 텍스트 visile용
        binding.sendnum.setOnClickListener{
            val phonenum = "+821002020202"
            viewModel.viewModelScope.launch{
                val ioJob = launch(Dispatchers.IO){
                    viewModel.startPhoneNumberVerification(phonenum)
                }
                val uiJob = launch(Dispatchers.Main) {
                    binding.verifyTxt.setText(R.string._분안에)
                    val colorStateList = ColorStateList.valueOf(resources.getColor(R.color.m1))
                    binding.verifyTxt.setTextColor(colorStateList)
                    val typeface = ResourcesCompat.getFont(requireContext(),R.font.spoqahansansneo_regular)
                    binding.verifyTxt.typeface = typeface
                    binding.verifyTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP,12f)
                    binding.verifyTxt.visibility = View.VISIBLE
                }
            }
        }


        binding.confirmBtn.setOnClickListener {
            if(viewModel.authcomplete.value == false)
            {
                val colorStateList = ColorStateList.valueOf(resources.getColor(R.color.m1))
                edittext3.backgroundTintList = colorStateList
                binding.verifyTxt.visibility = View.GONE
            }
            else{
                val colorStateList = ColorStateList.valueOf(resources.getColor(R.color.error))
                edittext3.backgroundTintList = colorStateList
                binding.verifyTxt.visibility = View.VISIBLE
                binding.verifyTxt.setText(R.string.인증번호가_불일치)
                val colorStatetxt = ColorStateList.valueOf(resources.getColor(R.color.error))
                binding.verifyTxt.setTextColor(colorStatetxt)
                val typeface = ResourcesCompat.getFont(requireContext(),R.font.spoqahansansneo_regular)
                binding.verifyTxt.typeface = typeface
                binding.verifyTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP,12f)
                binding.verifyTxt.visibility = View.VISIBLE
            }
            viewModel.viewModelScope.launch {
                viewModel.signInWithPhoneAuthCredential(binding.verifyid.text.toString())
            }
        }

        edittext4.setOnFocusChangeListener{
                view,isFocus ->
            viewModel._isFocusPW.postValue(isFocus)
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
                        val colorStateList = ColorStateList.valueOf(resources.getColor(R.color.error))
                        edittext4.backgroundTintList = colorStateList
                    }
                    else{
                        viewModel._isPWWrong.postValue(false)
                        val colorStateList = ColorStateList.valueOf(resources.getColor(R.color.m1))
                        edittext4.backgroundTintList = colorStateList
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        edittext5.setOnFocusChangeListener{
                view,isFocus->
            viewModel._isFocusPW2.postValue(isFocus)
        }

        edittext5.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString() == edittext4.text.toString())
                {
                    viewModel._isPWSame.postValue(true)
                    val colorStateList = ColorStateList.valueOf(resources.getColor(R.color.m1))
                    edittext5.backgroundTintList = colorStateList
                }
                else
                {
                    viewModel._isPWSame.postValue(false)
                    val colorStateList = ColorStateList.valueOf(resources.getColor(R.color.error))
                    edittext5.backgroundTintList = colorStateList
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
            else{
                Toast.makeText(requireContext(),"회원가입 단계가 완료되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    }


    fun moveToNext()
    {
        replaceFragment(SignupCFragment(), R.id.signup_fragmentcontainer)
    }


}