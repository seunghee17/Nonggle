package com.example.nongglenonggle.presentation.viewModel.worker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nongglenonggle.domain.entity.SeekerHomeFilterContent
import com.example.nongglenonggle.domain.entity.WorkerHomeData
import com.example.nongglenonggle.domain.usecase.FetchWorkerDataUseCase
import com.example.nongglenonggle.domain.usecase.GetAllNoticeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkerHomeViewModel @Inject constructor(
    private val fetchWorkerDataUseCase: FetchWorkerDataUseCase,
    private val getAllNoticeUseCase: GetAllNoticeUseCase
)
    :ViewModel(){
    private val _userDetail = MutableLiveData<WorkerHomeData?>()
    val userDetail : LiveData<WorkerHomeData?> = _userDetail

    private val _allNotice = MutableLiveData<List<SeekerHomeFilterContent>>()
    val allNotice:LiveData<List<SeekerHomeFilterContent>> = _allNotice

    fun fetchUserInfo(){
        viewModelScope.launch {
            val user = fetchWorkerDataUseCase.invoke()
            _userDetail.value = user
        }
    }
    fun getAllNotice(){
        viewModelScope.launch {
            getAllNoticeUseCase().collect{data->
                _allNotice.value = data
            }
        }
    }

    private val _isResume = MutableLiveData<Boolean>()
    val isResume:LiveData<Boolean> = _isResume

    private val _haveData = MutableLiveData<Boolean>()
    val haveData:LiveData<Boolean> = _haveData

    fun updateVisible(){
        if(allNotice.value != null){
            _haveData.value = true
        }
        else{
            _haveData.value = false
        }
    }

    fun fetchResumeVisible(){
        if(_userDetail.value?.refs != null){
            _isResume.value = true
        }
        else{
            _isResume.value = false
        }
    }

    init{
        fetchUserInfo()
        fetchResumeVisible()
        getAllNotice()
        _haveData.value = false
    }
}