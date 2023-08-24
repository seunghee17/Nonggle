package com.example.nongglenonggle.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentSignupBBinding
import com.example.nongglenonggle.viewModel.SignupBViewModel
import com.example.nongglenonggle.viewModel.SignupViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class SignupBFragment : Fragment() {
    private lateinit var viewModel:SignupBViewModel
    private lateinit var binding:FragmentSignupBBinding
    val auth = Firebase.auth
    var verificationId=""
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
        //firebase authentication을 위한 변수
        /*val phnum = binding.phnum
        val sendnum = binding.sendnum
        val verifyid = binding.verifyid
        val confirm = binding.confirmBtn*/

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
        //다음 버튼 binding
        val phnum = binding.phnum
        val sendnum = binding.sendnum
        val verifyid = binding.verifyid
        val confirm = binding.confirmBtn
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
        val callbacks = object:PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(e: FirebaseException) {
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                this@SignupBFragment.verificationId = verificationId
            }
        }
        auth.setLanguageCode("kr")
        sendnum.setOnClickListener{
            val phonenum="+821088644622"
            startPhoneNumberVerification(phonenum,callbacks)
        }
        confirm.setOnClickListener{
            if(verificationId.isNotEmpty())
            {
                val credential = PhoneAuthProvider.getCredential(verificationId,verifyid.text.toString())
                signInWithPhoneAuthCredential(credential)
            }
            else{
                Toast.makeText(getActivity(), "인증실패", Toast.LENGTH_SHORT)
            }
        }
    }

    private fun updatelineColor()
     {
        val activeLine= if(viewModel.isFocus) R.color.m1 else R.color.g_line
         binding.name.backgroundTintList = resources.getColorStateList(activeLine)
    }
    private fun startPhoneNumberVerification(phonenum:String, callbacks:OnVerificationStateChangedCallbacks)
    {
        val optionsCompat = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phonenum)
            .setTimeout(120L, TimeUnit.SECONDS)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(optionsCompat)
    }
    private fun signInWithPhoneAuthCredential(credential:PhoneAuthCredential)
    {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity())
            {
                task->
                if(task.isSuccessful){
                    val user= task.result?.user
                    Toast.makeText(getActivity(),"인증성공",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(getActivity(),"인증실패",Toast.LENGTH_SHORT).show()
                    Log.e("Verification", "verificationId is empty")
                }
            }
    }
}