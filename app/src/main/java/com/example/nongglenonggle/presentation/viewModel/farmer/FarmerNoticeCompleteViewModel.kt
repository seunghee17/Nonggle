package com.example.nongglenonggle.presentation.viewModel.farmer

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.nongglenonggle.domain.entity.NoticeContent
import com.example.nongglenonggle.domain.usecase.GetNoticeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FarmerNoticeCompleteViewModel @Inject constructor(
    private val getNoticeUseCase: GetNoticeUseCase
) : ViewModel() {
    //livedata블록 사용시 viewmodelscope내에서 코루틴 시작가능
    val noticeDetail : LiveData<NoticeContent?> = liveData {
        emitSource(getNoticeUseCase.invoke().asLiveData())
    }
}