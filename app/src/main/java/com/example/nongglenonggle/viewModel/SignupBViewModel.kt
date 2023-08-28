package com.example.nongglenonggle.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nongglenonggle.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class SignupBViewModel : ViewModel(){
    //focus시 액션을 구현하기 위헤
    private val _isFocus = MutableLiveData<Boolean>()
    val isFocus:LiveData<Boolean>
        get() = _isFocus
    //입력값에 따른 인증번호 버튼 활성화
    private val _isinputComplete = MutableLiveData<Boolean>()
    val isinputComplete:LiveData<Boolean>
        get() = _isinputComplete
    //입력값에 따른 확인버튼 활성화
    private val _isverifyend = MutableLiveData<Boolean>()
    val isverifyend:LiveData<Boolean>
        get() = _isverifyend
    //특정 조건판단하여 오류감지하여 밑줄색 변경
    private val _isErrorline = MutableLiveData<Boolean>()
    val isErrorline :LiveData<Boolean>
        get() = _isErrorline
    //기호 입력으로 인한 오류감지
    private val _ishypenError = MutableLiveData<Boolean>()
    val ishypenError:LiveData<Boolean>
        get() = _ishypenError
    //firebase 전화인증
    private val auth = Firebase.auth
    var verificationId: String = ""
    private val _resultAuthUser = MutableLiveData<Boolean>()
    val resultAuthUser:LiveData<Boolean>
        get() = _resultAuthUser
    //인증성공 여부 에러판단용
    val _authcomplete = MutableLiveData<Boolean>()
    val authcomplete:LiveData<Boolean>
        get() = _authcomplete
    //비밀번호 에러 판단용
    val _ispasswordavail=MutableLiveData<Boolean>()
    val ispasswordavail:LiveData<Boolean>
        get() = _ispasswordavail
    //비밀번호 선색상 판단용
    val _ispasswordLine1=MutableLiveData<Boolean>()
    val ispasswordLine1:LiveData<Boolean>
        get()=_ispasswordLine1
    var isSatisfy : Boolean = true
    //비밀번호 일치
    val _ispasswordsame=MutableLiveData<Boolean>()
    val ispasswordsame:LiveData<Boolean>
        get() = _ispasswordsame
    //비밀번호 일치 여부에 따른 선색상 에러 판단
    val _ispasswordLine2 = MutableLiveData<Boolean>()
    val ispasswordLine2 : LiveData<Boolean>
        get()=_ispasswordLine2
    var isSame : Boolean = true
    var isClicked = false
        private set
    fun onNextClick(){
        isClicked = !isClicked
    }

    init{
        _authcomplete.value = true
    }
    init{
        _ispasswordavail.value=true
    }
    init{
        _ispasswordsame.value=true
    }

    //화면전환을 위한것
    val _navigateToframentC = MutableLiveData<Boolean>()
    val navigateToframentC : LiveData<Boolean>
        get() = _navigateToframentC
    //포커싱 상태 업데이트
    fun setboxFocus(focused:Boolean)
    {
       _isFocus.value = focused //초기값은 false로 지정
    }
    //에러라인 업데이트
    fun updateErrorLineStatus(errorstate:Boolean)
    {
        _isErrorline.value=errorstate
    }

    //다음 버튼의 구현을 위해
    fun onNextbtnClick()
    {
        _navigateToframentC.value = true
    }
    //화면이동이 끝난후 value값 세팅
    fun doneNavigating()
    {
        _navigateToframentC.value=false
    }


    fun startPhoneNumberVerificcation(
        phoneNumber:String,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ){
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
    fun signInWithPhoneAuthCredential(verificationCode:String){
        if(verificationId.isNotEmpty()&& verificationCode.isNotEmpty()){
            val credential = PhoneAuthProvider.getCredential(verificationId,verificationCode)
            auth.signInWithCredential(credential)
                .addOnCompleteListener{task->
                    if(task.isSuccessful){
                        _resultAuthUser.value = true
                        _authcomplete.value = _resultAuthUser.value
                    }
                    else{
                        _resultAuthUser.value=false
                        _authcomplete.value = _resultAuthUser.value
                    }
                }
        }else{
            _resultAuthUser.value =false
            _authcomplete.value = _resultAuthUser.value
        }
    }


    //전화번호 형식 에러 메세지 출력을 위해
    fun updateErrorStatus(inputtext:String){
        _ishypenError.value = inputtext.contains("-")
        _isErrorline.value = _ishypenError.value
    }
    //입력값에 따른 인증번호 버튼 활성화 위해
    fun updateButtonStatus(input:String)
    {
        _isinputComplete.value = input.isNotEmpty()
    }
    //입력값에 따른 확인버튼 활성화 위해
    fun updateconfirmbtnStatus(input:String)
    {
        _isverifyend.value = input.isNotEmpty()
    }
    fun updatePwErrorStatus(input: String)
    {
        //  input.length 8~20
        for(s in input)
        {
           if(s !in 'A'..'Z' && s !in 'a'..'z' && s !in '0'..'9')
           {
               isSatisfy = false
           }
        }
        if(input.length>=8 && input.length<=20 && isSatisfy==true)
        {
            _ispasswordavail.value = true
        }
        else{
            _ispasswordavail.value=false
        }
        _ispasswordLine1.value = _ispasswordavail.value
    }
    fun updatePwErrorSame(input1:String, input2:String)
    {
        if(input1 == input2)
        {
            isSame=true
        }
        else{
            isSame=false
        }
        if(isSame==true)
        {
            _ispasswordsame.value = true
        }
        else{
            _ispasswordsame.value = false
        }
        _ispasswordLine2.value = _ispasswordsame.value
    }
}