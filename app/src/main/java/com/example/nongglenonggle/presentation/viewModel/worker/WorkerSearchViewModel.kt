package com.example.nongglenonggle.presentation.viewModel.worker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nongglenonggle.domain.entity.SeekerHomeFilterContent
import com.example.nongglenonggle.domain.entity.WorkerHomeData
import com.example.nongglenonggle.domain.entity.WorkerSearchRecommend
import com.example.nongglenonggle.domain.usecase.FetchWorkerDataUseCase
import com.example.nongglenonggle.domain.usecase.GetAllNoticeSubUseCase
import com.example.nongglenonggle.domain.usecase.GetNoticeBasedOnTypeUseCase
import com.google.firebase.firestore.DocumentReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
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
                Log.e("WorkerSearchViewModel", "Error fetching notices: $e")
                // 에러 처리 로직 (필요한 경우)
            }

        }
    }

    init{
        fetchUserInfo()
        fetchAllNoticeRef()
    }
}