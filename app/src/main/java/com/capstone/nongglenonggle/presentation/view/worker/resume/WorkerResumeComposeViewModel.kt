package com.capstone.nongglenonggle.presentation.view.worker.resume

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
): BaseViewModel<WorkerResumeContract.Event, WorkerResumeContract.State, WorkerResumeContract.Effect>(initialState = WorkerResumeContract.State()) {
    override fun reduceState(event: WorkerResumeContract.Event) {
        when(event) {
            is WorkerResumeContract.Event.SetGenderType -> {
                updateState(currentState.copy(selectedGender = event.gender))
            }
            is WorkerResumeContract.Event.ChangeCertificateState -> {
                updateState(currentState.copy(haveCertification = event.value))
            }
            is WorkerResumeContract.Event.InputAddressDetail -> {
                updateState(currentState.copy(addressTextfieldData = event.detailAddress))
            }
            is WorkerResumeContract.Event.ClearAddressData -> {
                updateState(currentState.copy(addressTextfieldData = ""))
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