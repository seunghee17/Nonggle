package com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4

import com.capstone.nongglenonggle.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4.ResumeStep4Contract.Effect as Step4Effect
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4.ResumeStep4Contract.Event as Step4Event
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4.ResumeStep4Contract.State as Step4State

@HiltViewModel
class ResumeStep4ViewModel @Inject constructor() :
    BaseViewModel<Step4Event, Step4State, Step4Effect>(
        initialState = Step4State()
    ) {
    override fun handleEvent(event: Step4Event) {
        super.handleEvent(event)
        when(event) {
            is Step4Event.ShowRegionBottomSheet -> {
                val currentBottomSheetVisibility = currentState.showSelectLocationBottomSheet
                updateState(currentState.copy(showSelectLocationBottomSheet = !currentBottomSheetVisibility))
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

            is Step4Event.UpdateCategoryChipState -> {
                val tempMap = currentState.totalPreferWorkCategoryMap
                val currentState = tempMap[event.key] ?: false
                val activateCount = tempMap.count { it.value }
                if(currentState==false && activateCount==3) {
                    postEffect(effect = Step4Effect.FailToastMessage("3개 이상 선택할 수 없습니다."))
                    return
                }
                tempMap.replace(event.key, !currentState)
                updateState(currentState.copy(totalPreferWorkCategoryList = tempMap))
            }
        }
    }
}