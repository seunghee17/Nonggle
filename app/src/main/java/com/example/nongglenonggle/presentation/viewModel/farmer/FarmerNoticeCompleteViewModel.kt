package com.example.nongglenonggle.presentation.viewModel.farmer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.nongglenonggle.domain.entity.NoticeContent
import com.example.nongglenonggle.domain.usecase.GetNoticeUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FarmerNoticeCompleteViewModel @Inject constructor(
    private val getNoticeUseCase: GetNoticeUseCase
) : ViewModel() {
    private val _noticeDetail = MutableStateFlow<NoticeContent?>(null)
    val isDataReady = MutableLiveData<Boolean>()
    val noticeDetail: StateFlow<NoticeContent?> get() = _noticeDetail
    init{
        //fetchNoticeDetail()
        isDataReady.value = false
    }
    fun fetchNoticeDetail(uid:String){
        viewModelScope.launch {
            val data= getNoticeUseCase.invoke(uid).collect{data->
                _noticeDetail.value = data
            }
            Log.d("NoticeCompleteVM", "$data")
            isDataReady.value = true
        }
    }
}