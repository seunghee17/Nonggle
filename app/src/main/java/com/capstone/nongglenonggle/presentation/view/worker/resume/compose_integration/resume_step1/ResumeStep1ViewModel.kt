package com.capstone.nongglenonggle.presentation.view.worker.resume.compose_integration.resume_step1

import android.net.Uri
import com.capstone.nongglenonggle.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.capstone.nongglenonggle.presentation.view.worker.resume.compose_integration.resume_step1.ResumeStep1Contract.Effect as effect
import com.capstone.nongglenonggle.presentation.view.worker.resume.compose_integration.resume_step1.ResumeStep1Contract.Event as event

@HiltViewModel
class ResumeStep1ViewModel @Inject constructor() :
    BaseViewModel<ResumeStep1Contract.Event, ResumeStep1Contract.State, ResumeStep1Contract.Effect>(
        initialState = ResumeStep1Contract.State()
    ) {
    override fun handleEvent(event: event) {
        when(event) {
            is event.SetGenderType -> {
                updateState(currentState.copy(selectedGender = event.gender))
            }
            is event.SetCertificateAvailable -> {
                updateState(currentState.copy(haveCertification = event.value))
            }
            is event.SetUserName -> {
                updateState(currentState.copy(userName = event.name))
            }
            is event.ClearUserName -> {
                updateState(currentState.copy(userName = ""))
            }
            is event.SetBirthDate -> {
                val userBirth = event.birthDate
                updateState(currentState.copy(
                    birthDate = userBirth,
                    birthDatePresnet = "${userBirth.year}년 ${userBirth.month}월 ${userBirth.date}일"
                ))
            }
            is event.SetUserCertificateDetail -> {
                updateState(currentState.copy(userCertificateType = event.certificate))
            }
            is event.ClearUserCertificateDetail -> {
                updateState(currentState.copy(userCertificateType = ""))
            }
            is event.AddCertificationChip -> {
                val tmpList = currentState.userCertificationList
                tmpList.add(event.certificationTitle)
                updateState(currentState.copy(userCertificationList = tmpList))
            }
        }
    }

    fun onImagePicked(uri: Uri) {
        updateState(currentState.copy(imageProfileUri = uri))
    }

    fun openGallery() {
        postEffect(effect = effect.OpenGallery)
    }
}