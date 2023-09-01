package com.example.nongglenonggle.viewModel.farmer.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignupDViewModel: ViewModel() {
    val _isSelect = MutableLiveData<Boolean>()
    val isSelect:LiveData<Boolean>
        get()=_isSelect

}