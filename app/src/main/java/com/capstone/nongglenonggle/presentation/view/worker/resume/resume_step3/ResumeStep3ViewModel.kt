package com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step3
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.capstone.nongglenonggle.core.base.BaseViewModel
import com.capstone.nongglenonggle.core.common.logger.AppLogger
import com.capstone.nongglenonggle.domain.usecase.worker.GetParentRegionListUseCase
import com.capstone.nongglenonggle.domain.usecase.worker.GetSubRegionListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step3.ResumeStep3Contract.Effect as Step3Effect
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step3.ResumeStep3Contract.Event as Step3Event
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step3.ResumeStep3Contract.State as Step3State

@HiltViewModel
class ResumeStep3ViewModel @Inject constructor(
    private val getParentRegionListUseCase: GetParentRegionListUseCase,
    private val getSubRegionListUseCase: GetSubRegionListUseCase,
) :BaseViewModel<Step3Event, Step3State, Step3Effect>(initialState = Step3State()) {

    override fun handleEvent(event: Step3Event) {
        super.handleEvent(event)
        when(event) {
            is Step3Event.ShowRegionBottomSheet -> {
                viewModelScope.launch {
                    updateState(currentState.copy(showSelectLocationBottomSheet = event.isOpen))
                    if(event.isOpen) {
                        updateState(currentState.copy(isLocationBottomSheetLoading = true))
                        getAllRegion()
                        updateState(currentState.copy(isLocationBottomSheetLoading = false))
                    }
                }
            }
            is Step3Event.AddPreferLocation -> {
                //리스트 상에서 선호 지역 선택 완료
                if(currentState.preferLocationList.size == 3) {
                    postEffect(effect = Step3Effect.FailToastMessage("3개 이상 선택할 수 없습니다."))
                    return
                }
                //선택 상태 업데이트
                updateState(currentState.copy(selectedSubRegion = event.subRegion))
                val tmpList = currentState.preferLocationList.toMutableList()
                tmpList.add(currentState.selectedParentRegion + event.subRegion)
                updateState(currentState.copy(preferLocationList = tmpList))
            }
            is Step3Event.RemovePreferLocation -> {
                if(currentState.preferLocationList.isEmpty()) return
                val tmpList = currentState.preferLocationList.toMutableList()
                tmpList.remove(event.region)
                updateState(currentState.copy(preferLocationList = tmpList))
            }

            is Step3Event.SelectPreferWorkCategory -> {
                val tmpList = currentState.selectedPreferWorkCategoryList.toMutableList()
                if(tmpList.contains(event.category)) {
                    tmpList.remove(event.category)
                } else if(tmpList.size < 3) {
                    tmpList.add(event.category)
                }
                updateState(currentState.copy(selectedPreferWorkCategoryList = tmpList))
            }

            is ResumeStep3Contract.Event.GetSubRegionList -> {
                updateState(currentState.copy(selectedParentRegion = event.parentRegion))
                viewModelScope.launch {
                    updateState(currentState.copy(selectedParentRegion = event.parentRegion))
                    getSubRegion(event.parentRegion)
                }
            }

            ResumeStep3Contract.Event.ClearSheetState -> {
                updateState(
                    currentState.copy(
                        regionList = emptyList(),
                        subRegionList = emptyList(),
                        selectedParentRegion = "",
                        selectedSubRegion = ""
                    )
                )
            }
        }
    }

    private suspend fun getAllRegion() {
        try {
            val regions = getParentRegionListUseCase.invoke()
            updateState(currentState.copy(regionList = regions))
            Log.d("TTAG", "${currentState.regionList}")
        } catch (e: Exception) {
            AppLogger.i("getAllRegion error at ResumeStep3ViewModel")
        }
    }

    private suspend fun getSubRegion(parentRegion: String) {
        try {
            val subRegions = getSubRegionListUseCase.invoke(parentRegion)
            updateState(currentState.copy(subRegionList = subRegions))
        } catch (e: Exception) {
            AppLogger.i("getSubRegion error at ResumeStep3ViewModel")
        }
    }
}