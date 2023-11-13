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
    val resumeDetail:StateFlow<ResumeContent?> get() =  _resumeDetail

    //val resumeLiveData: LiveData<ResumeContent?> = _resumeDetail.asLiveData()

    var certification:String=""
    var locations:String=""
    var items:String=""


     fun fetchResumeDetail(setting1:String, setting2:String){
        viewModelScope.launch {
            getResumeUseCase.invoke(setting1,setting2).collect{data->
               if(data != null){
                   _resumeDetail.value = data
               }
//                if(data?.careerList != null && resumeLiveData?.value?.careerList != null){
//                    certification = resumeLiveData?.value?.careerList?.joinToString(",") ?: "자격증 없음"
//                }
//                resumeLiveData?.value?.locationSelect?.let{locationList->
//                    locations = locationList.chunked(2).joinToString( ", " ) { it.joinToString(" ") }
//                }
//                resumeLiveData?.value?.desiredItem?.let{desiredItem->
//                    locations = desiredItem.chunked(2).joinToString( ", " ) { it.joinToString(" ") }
//                }
            }
        }


    }

}