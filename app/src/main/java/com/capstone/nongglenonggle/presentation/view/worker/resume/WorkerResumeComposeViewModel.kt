package com.capstone.nongglenonggle.presentation.view.worker.resume

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.capstone.nongglenonggle.core.base.BaseViewModel
import com.capstone.nongglenonggle.data.model.worker.ResumeModelClass
import com.capstone.nongglenonggle.domain.usecase.SetWorkderProfileImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
// Compose 전환에 사용될 viewmodel
@HiltViewModel
class WorkerResumeComposeViewModel @Inject constructor(
    private val setWorkerProfileImageUseCase: SetWorkderProfileImageUseCase,
): BaseViewModel<WorkerResumeContract.Event, WorkerResumeContract.State, WorkerResumeContract.Effect>(initialState = WorkerResumeContract.State()) {
    //viewmodel에 종속된 화면에서 사용할 데이터 정의
    val workerResume = ResumeModelClass()
    val certifications: MutableList<String> = mutableListOf()

    override fun handleEvent(event: WorkerResumeContract.Event) {
        when(event) {
            is WorkerResumeContract.Event.SetGenderType -> {
                updateState(currentState.copy(selectedGender = event.gender))
            }
            is WorkerResumeContract.Event.ChangeCertificateState -> {
                updateState(currentState.copy(haveCertification = event.value))
            }
            is WorkerResumeContract.Event.InputName -> {
                updateState(currentState.copy(addressTextfieldData = event.name))
            }
            is WorkerResumeContract.Event.ClearName -> {
                updateState(currentState.copy(addressTextfieldData = ""))
            }
            is WorkerResumeContract.Event.SetBirthDate -> {
                updateState(currentState.copy(
                    birthDate = event.birthDate,
                    birthDatePresnet = "${event.birthDate.year} 년 ${event.birthDate.month}월 ${event.birthDate.date}일"
                ))
            }
            is WorkerResumeContract.Event.WritingUserCertificateDetail -> {
                updateState(currentState.copy(userCertificateType = event.certificate))
            }
            is WorkerResumeContract.Event.ClearTextFieldUserCertificateDetail -> {
                updateState(currentState.copy(userCertificateType = ""))
            }
            is WorkerResumeContract.Event.addCertificationChip -> {
                val tmpList = currentState.userCertificationList
                tmpList.add(event.certificationTitle)
                updateState(currentState.copy(userCertificationList = tmpList))
            }
        }
    }

    fun openGallery() {
        postEffect(WorkerResumeContract.Effect.OpenGallery)
    }

    fun onImagePicked(uri: Uri) {
        viewModelScope.launch {
            setWorkerProfileImageUseCase.invoke(imageUri = uri)
        }
    }

}