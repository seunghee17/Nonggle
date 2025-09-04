package com.capstone.nongglenonggle.presentation.view.worker.resume.compose_integration

import android.net.Uri
import com.capstone.nongglenonggle.core.base.UiEffect
import com.capstone.nongglenonggle.core.base.UiEvent
import com.capstone.nongglenonggle.core.base.UiState
import java.util.Date

class WorkerResumeContract {
    data class State(
        val isLoading: Boolean = true,
        val addressTextfieldData: String = "",
        val selectedGender: String = "",
        val haveCertification: Boolean? = null,
        val imageProfileUri: Uri? = null,
        val birthDate: Date? = null,
        val birthDatePresnet: String = "생년월일을 선택해주세요.",
        val userCertificateType: String = "",
        val userCertificationList: MutableList<String> = mutableListOf()
    ): UiState

    sealed class Event: UiEvent {
        data class SetGenderType(val gender: String): Event()
        data class ChangeCertificateState(val value: Boolean): Event()
        object ClearName: Event()
        data class InputName(val name: String): Event()
        data class SetBirthDate(val birthDate: Date): Event()
        data class WritingUserCertificateDetail(val certificate: String): Event()
        object ClearTextFieldUserCertificateDetail: Event()
        data class addCertificationChip(val certificationTitle: String): Event()
    }

    sealed class Effect: UiEffect {
        object OpenGallery: Effect()
        object OpenBirthBottomSheet: Effect()
    }
}