package com.example.nongglenonggle.viewModel.farmer.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignupCViewModel:ViewModel() {
    //다음버튼 활성화 용
    var isComplete = false
        private set
    fun ToNextFragment() {
        isComplete = !isComplete
    }
    val _navigateToframentD = MutableLiveData<Boolean>()
    val navigateToframentD : LiveData<Boolean>
        get() = _navigateToframentD

    fun onNextbtnClick()
    {
        _navigateToframentD.value = true
    }
    fun doneNavigate()
    {
        _navigateToframentD.value=false
    }
}