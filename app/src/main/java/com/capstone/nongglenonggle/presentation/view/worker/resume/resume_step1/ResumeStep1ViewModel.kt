package com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step1

import android.net.Uri
import com.capstone.nongglenonggle.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.LinkedHashMap
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
            is Step1Event.UpDateDatePickerSheet -> {
                updateState(currentState.copy(showDatePickerSheet = event.sheetState))
            }
            is Step1Event.SetGenderType -> {
                selectWorkerGender(event.gender)
            }

            is Step1Event.GetImageFromGallery -> {
                onImagePicked(event.uri)
            }

            is Step1Event.OpenGallery -> {
                openGallery()
            }

            is Step1Event.SetCertificateAvailable -> {
                selectWorkerCertificationAvailable(event.updateState)
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
                val tmpList = currentState.userCertificationList.toMutableList()
                tmpList.add(event.certificationTitle)
                updateState(
                    currentState.copy(
                        userCertificationList = tmpList,
                        userCertificateType = ""
                    )
                )
            }
            is Step1Event.RemoveCertificationChip -> {
                val tmpList = currentState.userCertificationList.toMutableList()
                tmpList.remove(event.certificationTitle)
                updateState(currentState.copy(userCertificationList = tmpList))
            }
        }
    }

    private fun onImagePicked(uri: Uri) {
        updateState(currentState.copy(imageProfileUri = uri))
    }

    private fun selectWorkerGender(gender: String) {
        val genderStateMap = currentState.genderSelectedMap.toMutableMap()
        genderStateMap.keys.forEach { key ->
            genderStateMap[key] = false
        }
        genderStateMap[gender] = true
        updateState(currentState.copy(genderSelectedMap = LinkedHashMap(genderStateMap)))
    }

    private fun selectWorkerCertificationAvailable(updateState: String) {
        val certificateAvailableMap = currentState.certificationPossessionSelectedMap.toMutableMap()
        certificateAvailableMap.keys.forEach { key ->
            certificateAvailableMap[key] = false
        }
        certificateAvailableMap[updateState] = true
        updateState(currentState.copy(certificationPossessionSelectedMap = LinkedHashMap(certificateAvailableMap)))
    }

    private fun openGallery() {
        postEffect(effect = Step1Effect.OpenGallery)
    }

    
}