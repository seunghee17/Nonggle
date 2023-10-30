package com.example.nongglenonggle.presentation.viewModel.worker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nongglenonggle.domain.entity.WorkerHomeData
import com.example.nongglenonggle.domain.usecase.FetchWorkerDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkerHomeViewModel @Inject constructor(private val fetchWorkerDataUseCase: FetchWorkerDataUseCase)
    :ViewModel(){
    private val _userDetail = MutableLiveData<WorkerHomeData?>()
    val userDetail : LiveData<WorkerHomeData?> = _userDetail

    fun fetchUserInfo(){
        viewModelScope.launch {
            val user = fetchWorkerDataUseCase.invoke()
            _userDetail.value = user
        }
    }

    private val _isResume = MutableLiveData<Boolean>()
    val isResume:LiveData<Boolean> = _isResume

    fun fetchResumeVisible(){
        if(_userDetail.value?.resume != null){
            _isResume.value = true
        }
        else{
            _isResume.value = false
        }
    }
}