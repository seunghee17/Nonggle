package com.example.nongglenonggle.viewModel.signup

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nongglenonggle.di.VerificationModule.provideVerificationCallbacks
import com.example.nongglenonggle.usecase.UpdateAddressUseCase
import com.example.nongglenonggle.usecase.VerificationEvent
import com.example.nongglenonggle.view.signup.AddressSearchFragment
import com.example.nongglenonggle.view.signup.SignupDFragment
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val updateAddressUseCase: UpdateAddressUseCase, @ApplicationContext private val context: Context) :ViewModel(), VerificationEvent {
    private val auth = Firebase.auth
    private val callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks by lazy{
        provideVerificationCallbacks(this)
    }

    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
        _authcomplete.value = true
    }

    override fun onVerificationFailed(e: FirebaseException) {
        _authcomplete.value=false
    }

    override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
        this.verificationId = verificationId
    }

    //현재 fragment 읽어오기
    val currentFragment= MutableLiveData<Fragment>()

    //도로명 주소 변수 정의
    val _addressResult = MutableLiveData<String>()
    val addressResult:LiveData<String>
        get() = _addressResult
    //textview visible용
    val _isdata = MutableLiveData<Boolean>()
    val isdata:LiveData<Boolean>
        get() = _isdata

    //다음버튼 활성화용
    val _isActiveNext = MutableLiveData<Boolean>()
    val isActiveNext:LiveData<Boolean> = _isActiveNext


    //구인자유형 선택
    val _isHire = MutableLiveData<Boolean>(false)
    val isHire:LiveData<Boolean> = _isHire

    //구직자 유형 선택
    val _isWorker = MutableLiveData<Boolean>()
    val isWorker:LiveData<Boolean> = _isWorker

    //인증성공 여부 에러판단용
    val _authcomplete = MutableLiveData<Boolean>()
    val authcomplete: LiveData<Boolean>
        get() = _authcomplete
    var verificationId: String = ""


    //이름 입력칸
    val _isFocusName = MutableLiveData<Boolean>()
    val isFocusName:LiveData<Boolean> = _isFocusName

    //아이디 입력칸
    val _isFocusId = MutableLiveData<Boolean>()
    val isFoucsId :LiveData<Boolean> = _isFocusId

    //아이디 입력형식 오류감지
    val _isIdWrong = MutableLiveData<Boolean>()
    val isIdWrong:LiveData<Boolean> = _isIdWrong

    //인증번호 포커싱
    val _isFocusVerification = MutableLiveData<Boolean>()
    val isFocusVerification:LiveData<Boolean> = _isFocusVerification

    //비밀번호 포커싱
    val _isFocusPW = MutableLiveData<Boolean>()
    val isFocusPW:LiveData<Boolean> = _isFocusPW

    //비밀번호 형식 판단
    val _isPWWrong = MutableLiveData<Boolean>()
    val isPWWrong:LiveData<Boolean> = _isPWWrong

    //비밀번호2 포커싱
    val _isFocusPW2 = MutableLiveData<Boolean>()
    val isFocusPW2:LiveData<Boolean> = _isFocusPW2

    //비밀번호 일치여부 판단
    val _isPWSame = MutableLiveData<Boolean>()
    val isPWSame:LiveData<Boolean> = _isPWSame

    private val _resultAuthUser = MutableLiveData<Boolean>()
    val resultAuthUser: LiveData<Boolean>
        get() = _resultAuthUser

    private val firestore = Firebase.firestore

    var isComplete = false
        private set

    fun startPhoneNumberVerification(phonenum:String)
    {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phonenum)
            .setTimeout(120L, TimeUnit.SECONDS)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    //회원가입 진행
    suspend fun signUpWithEmailPasswordAndPhoneNumber(email: String,password:String,name:String)
    {
        return suspendCancellableCoroutine {
                continuation->
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener{task->
                    if(task.isSuccessful)
                    {
                        val user: FirebaseUser? = auth.currentUser
                        user?.let{
                            val userDocument = firestore.collection("Farmer").document("User").collection("Users").document(it.uid)
                            userDocument.set(
                                mapOf(
                                    "uid" to it.uid,
                                    "phoneNum" to email,
                                    "userName" to name
                                )
                            )
                        }
                        //코루틴 사용할 수 있지 않을까?
                        Log.e("signup", "success")
                    }
                    else{
                        Log.e("signup", "fail")
                    }
                }
        }
    }

    fun updateAddress(data:String)
    {
        viewModelScope.launch {
            updateAddressUseCase.execute(data)
            _addressResult.value = updateAddressUseCase.getAddress()
            _isdata.value = updateAddressUseCase.hasData()
        }
    }

    fun updateHireType():Boolean {
        _isHire.value = !(_isHire.value ?: false)
        _isActiveNext.value = _isHire.value
        Log.e("fuck", "${isHire.value}")
        return isHire.value ?: false

    }

    fun updateWorkerType() {
        _isWorker.value= !(_isWorker.value ?: false)
        _isActiveNext.value = _isWorker.value
    }


   //보여지는 첫화면 세팅
    init{
       _isdata.postValue(false)
       //_isHire.postValue(false)
       _isWorker.postValue(false)
       _isActiveNext.postValue(false)
       _isFocusName.postValue(false)
       _isFocusId.postValue(false)
       _isIdWrong.postValue(false)
       _isFocusVerification.postValue(false)
       _isFocusPW.postValue(false)
       _isPWWrong.postValue(false)
       _isPWSame.postValue(true)
       _isFocusPW2.postValue(false)
    }

    suspend fun signInWithPhoneAuthCredential(verificationCode: String):Boolean {
        return suspendCancellableCoroutine { continuation ->
            if (verificationId.isNotEmpty() && verificationCode.isNotEmpty()) {
                val credential = PhoneAuthProvider.getCredential(verificationId, verificationCode)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _resultAuthUser.value = true
                            _authcomplete.value = _resultAuthUser.value
                        } else {
                            _resultAuthUser.value = false
                            _authcomplete.value = _resultAuthUser.value
                        }
                    }
            } else {
                _resultAuthUser.value = false
                _authcomplete.value = _resultAuthUser.value
            }
        }
    }


    fun navigateToAddressFragment(){
        currentFragment.value = AddressSearchFragment()
    }
    fun navigateToDFragment()
    {
        currentFragment.value = SignupDFragment()
    }


}