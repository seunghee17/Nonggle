package com.capstone.nongglenonggle.presentation.viewModel.farmer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FarmerSearchViewModel : ViewModel() {
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    val _workType = MutableLiveData<String>()
    val workType:LiveData<String> = _workType

    fun updateInfo(name:String){
        _userName.postValue(name)
    }

}