package com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4

import com.capstone.nongglenonggle.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4.ResumeStep3Contract.Effect as Step4Effect
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4.ResumeStep3Contract.Event as Step4Event
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4.ResumeStep3Contract.State as Step4State

@HiltViewModel
class ResumeStep3ViewModel @Inject constructor() :
    BaseViewModel<Step4Event, Step4State, Step4Effect>(
        initialState = Step4State()
    ) {
    init {

    }

    override fun handleEvent(event: Step4Event) {
        super.handleEvent(event)
        when(event) {
            is Step4Event.ShowRegionBottomSheet -> {
                updateState(currentState.copy(showSelectLocationBottomSheet = event.openState))
            }
            is Step4Event.AddPreferLocation -> {
                val tmpList = currentState.preferLocationList
                if(tmpList.size == 3) postEffect(effect = Step4Effect.FailToastMessage("3개 이상 선택할 수 없습니다."))
                tmpList.add(event.region)
                updateState(currentState.copy(preferLocationList = tmpList))
            }
            is Step4Event.RemovePreferLocation -> {
                val tmpList = currentState.preferLocationList
                tmpList.remove(event.region)
                updateState(currentState.copy(preferLocationList = tmpList))
            }

            is Step4Event.SelectPreferWorkCategory -> {
                val tmpList = currentState.selectedPreferWorkCategoryList.toMutableList()
                if(tmpList.contains(event.category)) {
                    tmpList.remove(event.category)
                } else if(tmpList.size < 3) {
                    tmpList.add(event.category)
                }
                updateState(currentState.copy(selectedPreferWorkCategoryList = tmpList))
            }
        }
    }
}