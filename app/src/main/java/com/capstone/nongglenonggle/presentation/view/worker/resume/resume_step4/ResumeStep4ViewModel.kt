package com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4

import com.capstone.nongglenonggle.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4.ResumeStep4Contract.Effect as Effect
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4.ResumeStep4Contract.Event as Event
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4.ResumeStep4Contract.State as State

@HiltViewModel
class ResumeStep4ViewModel @Inject constructor() :
    BaseViewModel<Event, State, Effect>(
        initialState = State()
    ) {
    override fun handleEvent(event: Event) {
        super.handleEvent(event)
        when(event) {
            is Event.AddWorkCategoryChip -> {
                val tempList = currentState.preferWorkCategoryList
                if(tempList.size!=3) {
                    tempList.add(event.value)
                    updateState(currentState.copy(preferWorkCategoryList = tempList))
                } else {
                    postEffect(effect = Effect.FailToastMessage("3개 이상 초과할 수 없습니다")) //개수 초과해서 추가 불가능함을 알리기
                }
            }
            is Event.RemoveWorkCategoryChip -> {
                val tempList = currentState.preferWorkCategoryList
                if(tempList.size!=0) {
                    tempList.remove(event.value)
                }
                updateState(currentState.copy(preferWorkCategoryList = tempList))
            }
        }
    }
}