package com.capstone.nongglenonggle.presentation.view.worker.resume.compose_integration

import com.capstone.nongglenonggle.core.base.BaseViewModel
import com.capstone.nongglenonggle.domain.usecase.SetWorkderProfileImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.capstone.nongglenonggle.presentation.view.worker.resume.compose_integration.WorkerResumeContract.Event.Step1 as Step1

// Compose 전환에 사용될 viewmodel
@HiltViewModel
class WorkerResumeComposeViewModel @Inject constructor(
    private val setWorkerProfileImageUseCase: SetWorkderProfileImageUseCase,
) : BaseViewModel<WorkerResumeContract.Event, WorkerResumeContract.State, WorkerResumeContract.Effect>(
    initialState = WorkerResumeContract.State()
) {

    init {
        onEvent<WorkerResumeContract.Event.Step2> { e ->
            when(e) {
                is WorkerResumeContract.Event.Step2.SetCareerTitle -> {
                    updateState(
                        currentState.copy(
                            step2 = currentState.step2.copy(careerTextFieldValue = e.title)
                        )
                    )
                }
                is WorkerResumeContract.Event.Step2.ClearCareerTitle -> {
                    updateState(
                        currentState.copy(
                            step2 = currentState.step2.copy(careerTextFieldValue = "")
                        )
                    )
                }
                is WorkerResumeContract.Event.Step2.SetWorkPeriodRange -> {
                    updateState(
                        currentState.copy(
                            step2 = currentState.step2.copy(
                                careerAddBottomSheetState = currentState.step2.careerAddBottomSheetState.copy(
                                    isLongerThenMonth = e.isMonthOver
                                )
                            )
                        )
                    )
                }
                is WorkerResumeContract.Event.Step2.SetCareerDetail -> {
                    updateState(
                        currentState.copy(
                            step2 = currentState.step2.copy(
                                careerAddBottomSheetState = currentState.step2.careerAddBottomSheetState.copy(
                                    careerDetailContent = e.detail
                                )
                            )
                        )
                    )
                }
            }
        }
        onEvent<WorkerResumeContract.Event.Step4> { e->
            when(e) {
                is WorkerResumeContract.Event.Step4.AddWorkCategoryChip -> {
                    val tempList = currentState.step4.preferWorkCategoryList
                    if(tempList.size!=3) {
                        tempList.add(e.value)
                    } else {
                        postEffect(effect = WorkerResumeContract.Effect.UnAvailableToastmessage("3개 이상 초과할 수 없습니다")) //개수 초과해서 추가 불가능함을 알리기
                    }
                }
                is WorkerResumeContract.Event.Step4.RemoveWorkCategoryChip -> {
                    val tempList = currentState.step4.preferWorkCategoryList
                    if(tempList.size!=0) {
                        tempList.remove(e.value)
                    }
                }
            }

        }
    }

}
