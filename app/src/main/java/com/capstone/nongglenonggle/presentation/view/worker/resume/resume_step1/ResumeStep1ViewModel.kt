package com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step1

import android.net.Uri
import com.capstone.nongglenonggle.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step1.ResumeStep1Contract.Effect as Step1Effect
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step1.ResumeStep1Contract.Event as Step1Event
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step1.ResumeStep1Contract.State as Step1State

@HiltViewModel
class ResumeStep1ViewModel @Inject constructor() :
    BaseViewModel<Step1Event, Step1State, Step1Effect>(
        initialState = ResumeStep1Contract.State()
    ) {
    override fun handleEvent(event: Step1Event) {
        when (event) {
            is Step1Event.SetGenderType -> {
                updateState(currentState.copy(selectedGender = event.gender))
            }

            is Step1Event.SetCertificateAvailable -> {
                updateState(currentState.copy(haveCertification = event.value))
            }

            is Step1Event.SetUserName -> {
                updateState(currentState.copy(userName = event.name))
            }

            is Step1Event.ClearUserName -> {
                updateState(currentState.copy(userName = ""))
            }

            is Step1Event.SetBirthDate -> {
                val userBirth = event.birthDate
                updateState(
                    currentState.copy(
                        birthDate = userBirth,
                        birthDatePresnet = "${userBirth.year}년 ${userBirth.month}월 ${userBirth.date}일"
                    )
                )
            }

            is Step1Event.SetUserCertificateDetail -> {
                updateState(currentState.copy(userCertificateType = event.certificate))
            }

            is Step1Event.ClearUserCertificateDetail -> {
                updateState(currentState.copy(userCertificateType = ""))
            }

            is Step1Event.AddCertificationChip -> {
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
        postEffect(effect = Step1Effect.OpenGallery)
    }
}