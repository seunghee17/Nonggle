package com.example.nongglenonggle.presentation.viewModel.signup

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nongglenonggle.presentation.di.VerificationModule.provideVerificationCallbacks
import com.example.nongglenonggle.domain.usecase.UpdateAddressUseCase
import com.example.nongglenonggle.domain.usecase.VerificationEvent
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
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
class SignupViewModel @Inject constructor(private val updateAddressUseCase: UpdateAddressUseCase, @ApplicationContext private val context: Context) :ViewModel(),
    VerificationEvent {
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

    //아이디 버튼 활성화
    val _IdBtnActive = MutableLiveData<Boolean>(false)
    val IdBtnActive:LiveData<Boolean> = _IdBtnActive

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

    val _cstepActive = MutableLiveData<Boolean>(false)
    val cstepActive:LiveData<Boolean> = _cstepActive

    //카테고리 상태 저장용
    val buttonupdateds: MutableLiveData<MutableMap<String, Boolean>> = MutableLiveData()

    private var buttoncnt = 0

    //유저의 uid값 livedata에 저장
    private val _UserUID = MutableLiveData<String>()
    val UserUID : LiveData<String> = _UserUID

    //dstep 다음버튼 활성화
    private val _dstepActive=MutableLiveData<Boolean>()
    val dstepActive:LiveData<Boolean> = _dstepActive


    fun startPhoneNumberVerification(phonenum:String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phonenum)
            .setTimeout(120L, TimeUnit.SECONDS)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    //회원가입 진행
    suspend fun signUpWithEmailPasswordAndPhoneNumber(email: String,password:String,name:String) {
        return suspendCancellableCoroutine {
                continuation->
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener{task->
                    if(task.isSuccessful)
                    {
                        val user: FirebaseUser? = auth.currentUser
                        //구인자
                        if(isHire.value == true){
                            user?.let{
                                val userDocument = firestore.collection("Farmer").document(it.uid)
                                _UserUID.postValue(it.uid)
                                userDocument.set(
                                    mapOf(
                                        "uid" to it.uid,
                                        "phoneNum" to email,
                                        "userName" to name
                                    )
                                )
                            }
                        }
                        else if(isWorker.value == true){
                            user?.let{
                                val userDocument = firestore.collection("Worker").document(it.uid)
                                _UserUID.postValue(it.uid)
                                userDocument.set(
                                    mapOf(
                                        "uid" to it.uid,
                                        "phoneNum" to email,
                                        "userName" to name
                                    )
                                )
                            }
                        }
                        //코루틴 사용할 수 있지 않을까?
                        Log.e("signup", "회원가입 완료")
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

    fun updateHireType() {
        _isHire.value = !(_isHire.value ?: false)
        _isWorker.value = !(_isHire.value!!)
        _isActiveNext.value = _isHire.value

    }

    fun updateWorkerType() {
        _isWorker.value= !(_isWorker.value ?: false)
        _isHire.value= !(_isWorker.value!!)
        _isActiveNext.value = _isWorker.value
    }


   //보여지는 첫화면 세팅
    init{
       _isdata.postValue(false)
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
       buttonupdateds.value = mutableMapOf()
       _dstepActive.postValue(false)

    }

    //버튼클릭시 변경되는
    fun onButtonClick(category:String){
        if(buttoncnt <3){
            val currentColor = buttonupdateds.value?.get(category) ?: false
            if(!currentColor){
                val updateMap = buttonupdateds.value?: mutableMapOf()
                updateMap[category]=true
                buttonupdateds.value = updateMap
                buttoncnt++
                Log.e("please", "${buttonupdateds.value}")
                Log.e("signup", "success/${UserUID.value}")
            }
        }
        getSelectedCategory()
    }



    //선택한 카테고리 list화
    fun getSelectedCategory() : List<String>{
        val selectedButtonId = buttonupdateds.value?.filter { it.value }?.keys ?: emptySet()
        //데이터베이스에 들어갈 리스트
        val selectedButtonText = mutableListOf<String>()

        for(buttonId in selectedButtonId){
            when(buttonId){
                "category1" -> selectedButtonText.add("식량작물")
                "category2" -> selectedButtonText.add("채소")
                "category3" -> selectedButtonText.add("과수")
                "category4" -> selectedButtonText.add("특용작물")
                "category5" -> selectedButtonText.add("화훼")
                "category6" -> selectedButtonText.add("축산")
                "category7" -> selectedButtonText.add("농기계작업")
                "category8" -> selectedButtonText.add("기타")
            }
        }
        if(selectedButtonText.isNotEmpty()){
            _dstepActive.postValue(true)
        }
        return selectedButtonText
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
}