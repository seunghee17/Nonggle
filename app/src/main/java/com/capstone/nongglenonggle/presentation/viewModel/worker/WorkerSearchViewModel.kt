package com.capstone.nongglenonggle.presentation.viewModel.worker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.nongglenonggle.domain.entity.WorkerHomeData
import com.capstone.nongglenonggle.domain.entity.WorkerSearchRecommend
import com.capstone.nongglenonggle.domain.usecase.FetchWorkerDataUseCase
import com.capstone.nongglenonggle.domain.usecase.GetAllNoticeSubUseCase
import com.capstone.nongglenonggle.domain.usecase.GetNoticeBasedOnTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkerSearchViewModel @Inject constructor(
    private val fetchWorkerDataUseCase: FetchWorkerDataUseCase,
    private val getAllNoticeSubUseCase: GetAllNoticeSubUseCase,
    private val getNoticeBasedOnTypeUseCase: GetNoticeBasedOnTypeUseCase
) :ViewModel() {
    private val _userDetail = MutableLiveData<WorkerHomeData?>()
    val userDetail : LiveData<WorkerHomeData?> = _userDetail

    private val _subNotice = MutableLiveData<List<WorkerSearchRecommend>>()
    val subNotice:LiveData<List<WorkerSearchRecommend>> = _subNotice



    val _workType = MutableLiveData<String>()
    val worktype :LiveData<String> = _workType


    fun fetchUserInfo(){
        viewModelScope.launch {
            val user = fetchWorkerDataUseCase.invoke()
            _userDetail.value = user
        }
    }
    fun fetchAllNoticeRef(){
        viewModelScope.launch {
            getAllNoticeSubUseCase().collect{data->
                _subNotice.value = data
            }
        }
    }

    fun getNoticeBasedOntype(type: String) {
        viewModelScope.launch {
            try {
                getNoticeBasedOnTypeUseCase(type).collect { data ->
                    _subNotice.value = data
                }
            } catch (e: Exception) {
                // 에러 처리 로직
            }

        }
    }

    init{
        fetchUserInfo()
        fetchAllNoticeRef()
    }
}