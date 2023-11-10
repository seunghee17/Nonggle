package com.example.nongglenonggle.presentation.viewModel.worker

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.nongglenonggle.domain.entity.Model
import com.example.nongglenonggle.domain.entity.ResumeContent
import com.example.nongglenonggle.domain.usecase.GetResumeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResumeCompleteViewModel @Inject constructor(
    private val getResumeUseCase: GetResumeUseCase
): ViewModel(){
    private val _resumeDetail = MutableStateFlow<ResumeContent?>(null)
    val resumeDetail:StateFlow<ResumeContent?> = _resumeDetail

    val resumeLiveData: LiveData<ResumeContent?> = _resumeDetail.asLiveData()

    var certification:String=""
    var locations:String=""
    var items:String=""


     fun fetchResumeDetail(setting1:String, setting2:String){
        viewModelScope.launch {
            getResumeUseCase.invoke(setting1,setting2).collect{data->
                _resumeDetail.value = data
                if(data?.careerList != null && resumeLiveData?.value?.careerList != null){
                    certification = resumeLiveData?.value?.careerList?.joinToString(",") ?: "자격증 없음"
                }
                if(resumeLiveData?.value?.locationSelect != null){
                    if(resumeLiveData?.value?.locationSelect?.size == 2){
                        locations = "${resumeLiveData?.value?.locationSelect?.get(0)} ${resumeLiveData?.value?.locationSelect?.get(1)}"
                    }
                    if(resumeLiveData?.value?.locationSelect?.size == 4){
                        locations = "${resumeLiveData?.value?.locationSelect?.get(0)} ${resumeLiveData?.value?.locationSelect?.get(1)}, ${resumeLiveData?.value?.locationSelect?.get(2)} ${resumeLiveData?.value?.locationSelect?.get(3)}"
                    }
                    if(resumeLiveData?.value?.locationSelect?.size == 6){
                        locations = "${resumeLiveData?.value?.locationSelect?.get(0)} ${resumeLiveData?.value?.locationSelect?.get(1)}, ${resumeLiveData?.value?.locationSelect?.get(2)} ${resumeLiveData?.value?.locationSelect?.get(3)}, ${resumeLiveData?.value?.locationSelect?.get(4)} ${resumeLiveData?.value?.locationSelect?.get(5)}"
                    }
                }
                if(resumeLiveData?.value?.desiredItem != null){
                    if(resumeLiveData?.value?.desiredItem?.size ==1){
                        items = "${resumeLiveData?.value?.desiredItem?.get(0)}"
                    }
                    if(resumeLiveData?.value?.desiredItem?.size ==2){
                        items = "${resumeLiveData?.value?.desiredItem?.get(0)}, ${resumeLiveData?.value?.desiredItem?.get(1)}"
                    }
                    if(resumeLiveData?.value?.desiredItem?.size ==3){
                        items = "${resumeLiveData?.value?.desiredItem?.get(0)}, ${resumeLiveData?.value?.desiredItem?.get(1)}, ${resumeLiveData?.value?.desiredItem?.get(2)}"
                    }
                }
            }
        }


    }

}