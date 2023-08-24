package com.example.nongglenonggle.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

class SignupBViewModel : ViewModel(){
    //focus시 액션을 구현하기 위헤
    var isFocus = false
        private set

    //화면전환을 위한것
    val _navigateToframentC = MutableLiveData<Boolean>()
    val navigateToframentC : LiveData<Boolean>
        get() = _navigateToframentC
    //문자로 받은 인증번호
    lateinit var authNum: String
    var phnum:String=""
    val etPhoneNum = MutableLiveData<String>("")
    val etAuthNum = MutableLiveData<String>("")
    private val _requestAuth = MutableLiveData<Boolean>()
    //인증번호 요청했는지 유무
    private val _authState = MutableLiveData<Boolean>()
    private val _resultAuthUser = MutableLiveData<Boolean>()
    val requestAuth : LiveData<Boolean> get() = _requestAuth
    val authState : LiveData<Boolean> get() = _authState
    val resultAuthUser : LiveData<Boolean> get() = _resultAuthUser
    fun setboxFocus()
    {
        isFocus= !isFocus
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
}