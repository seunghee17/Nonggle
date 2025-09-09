package com.capstone.nongglenonggle.presentation.view.worker.resume.compose_integration

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.capstone.nongglenonggle.core.base.BaseViewModel
import com.capstone.nongglenonggle.domain.usecase.SetWorkderProfileImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// Compose 전환에 사용될 viewmodel
@HiltViewModel
class WorkerResumeComposeViewModel @Inject constructor(
    private val setWorkerProfileImageUseCase: SetWorkderProfileImageUseCase,
) : BaseViewModel<WorkerResumeContract.Event, WorkerResumeContract.State, WorkerResumeContract.Effect>(
    initialState = WorkerResumeContract.State()
) {

    init {
        onEvent<WorkerResumeContract.Event.Step1> { e ->
            when (e) {
                is WorkerResumeContract.Event.Step1.SetGenderType -> {
                    updateState(
                        currentState.copy(
                            step1 = currentState.step1.copy(selectedGender = e.gender)
                        )
                    )
                }

                is WorkerResumeContract.Event.Step1.SetCertificateAvailable -> {
                    currentState.copy(
                        step1 = currentState.step1.copy(haveCertification = e.value)
                    )
                }

                is WorkerResumeContract.Event.Step1.SetUserName -> {
                    currentState.copy(
                        step1 = currentState.step1.copy(userName = e.name)
                    )
                }

                is WorkerResumeContract.Event.Step1.ClearUserName -> {
                    currentState.copy(
                        step1 = currentState.step1.copy(userName = "")
                    )
                }

                is WorkerResumeContract.Event.Step1.SetBirthDate -> {
                    val userBirth = e.birthDate
                    currentState.copy(
                        step1 = currentState.step1.copy(
                            birthDate = userBirth,
                            birthDatePresnet = "${userBirth.year}년 ${userBirth.month}월 ${userBirth.date}일"
                        )
                    )
                }

                is WorkerResumeContract.Event.Step1.SetUserCertificateDetail -> {
                    currentState.copy(
                        step1 = currentState.step1.copy(userCertificateType = e.certificate)
                    )
                }

                is WorkerResumeContract.Event.Step1.ClearUserCertificateDetail -> {
                    currentState.copy(
                        step1 = currentState.step1.copy(userCertificateType = "")
                    )
                }

                is WorkerResumeContract.Event.Step1.AddCertificationChip -> {
                    val tmpList = currentState.step1.userCertificationList
                    tmpList.add(e.certificationTitle)
                    currentState.copy(
                        step1 = currentState.step1.copy(userCertificationList = tmpList)
                    )
                }
            }
        }
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

    fun openGallery() {
        postEffect(WorkerResumeContract.Effect.Step1.OpenGallery)
    }

    fun onImagePicked(uri: Uri) {
        viewModelScope.launch {
            setWorkerProfileImageUseCase.invoke(imageUri = uri)
        }
    }
}
