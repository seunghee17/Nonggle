package com.example.nongglenonggle.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignupBViewModel : ViewModel(){
    //focus시 액션을 구현하기 위헤
    var isFocus = false
        private set

    //화면전환을 위한것
    val _navigateToframentC = MutableLiveData<Boolean>()
    val navigateToframentC : LiveData<Boolean>
        get() = _navigateToframentC

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