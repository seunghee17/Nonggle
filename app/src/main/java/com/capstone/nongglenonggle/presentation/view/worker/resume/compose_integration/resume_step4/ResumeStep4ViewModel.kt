package com.capstone.nongglenonggle.presentation.view.worker.resume.compose_integration.resume_step4

import com.capstone.nongglenonggle.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.capstone.nongglenonggle.presentation.view.worker.resume.compose_integration.resume_step4.ResumeStep4Contract.Effect as effect
import com.capstone.nongglenonggle.presentation.view.worker.resume.compose_integration.resume_step4.ResumeStep4Contract.Event as event

@HiltViewModel
class ResumeStep4ViewModel @Inject constructor() :
    BaseViewModel<ResumeStep4Contract.Event, ResumeStep4Contract.State, ResumeStep4Contract.Effect>(
        initialState = ResumeStep4Contract.State()
    ) {
    override fun handleEvent(event: ResumeStep4Contract.Event) {
        super.handleEvent(event)
        when(event) {
            is event.AddWorkCategoryChip -> {
                val tempList = currentState.preferWorkCategoryList
                if(tempList.size!=3) {
                    tempList.add(event.value)
                } else {
                    postEffect(effect = effect.UnAvailableToastmessage("3개 이상 초과할 수 없습니다")) //개수 초과해서 추가 불가능함을 알리기
                }
            }
            is event.RemoveWorkCategoryChip -> {
                val tempList = currentState.preferWorkCategoryList
                if(tempList.size!=0) {
                    tempList.remove(event.value)
                }
                updateState(currentState.copy(totalPreferWorkCategoryList = tempList))
            }
        }
    }
}