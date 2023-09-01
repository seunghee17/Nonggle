package com.example.nongglenonggle.viewModel.farmer.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nongglenonggle.R

class SignupAViewModel : ViewModel() {
    //화면전환을 위한것
    val _navigateToframentB = MutableLiveData<Boolean>()
    val navigateToframentB : LiveData<Boolean>
        get() = _navigateToframentB

    var isClicked = false
        private set


    fun onFrameLayoutClick(){
        isClicked = !isClicked
    }



    //다음 버튼의 구현을 위해
    fun onNextbtnClick()
    {
        _navigateToframentB.value = true
    }
    //화면이동이 끝난후 value값 세팅
    fun doneNavigating()
    {
        _navigateToframentB.value=false
    }
    val typeBackground:LiveData<Int> = MutableLiveData<Int>().apply{
        value = R.color.m1
    }
}