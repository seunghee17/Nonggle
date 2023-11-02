package com.example.nongglenonggle.presentation.viewModel.farmer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nongglenonggle.domain.entity.FarmerHomeData
import com.example.nongglenonggle.domain.usecase.FetchFarmerDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FarmerHomeViewModel @Inject constructor(
    private val fetchFarmerDataUseCase: FetchFarmerDataUseCase)
    : ViewModel(){
    private val _userDetail = MutableLiveData<FarmerHomeData?>()
    val userDetail : LiveData<FarmerHomeData?> = _userDetail
    fun fetchUserInfo(){
        viewModelScope.launch {
            val user = fetchFarmerDataUseCase.invoke()
            _userDetail.value = user
            fetchNoticeVisible()
        }
    }
    private val _isNotice = MutableLiveData<Boolean>()
    val isNotice:LiveData<Boolean> = _isNotice
    var resumeNum:Int = 0

    fun fetchNoticeVisible(){
        if(_userDetail.value?.notice != null){
            _isNotice.value = true
            resumeNum = 1
        }
        else{
            _isNotice.value = false
            resumeNum = 0
        }
    }



}