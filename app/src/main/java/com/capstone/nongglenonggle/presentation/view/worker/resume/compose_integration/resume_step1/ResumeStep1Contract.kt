package com.capstone.nongglenonggle.presentation.view.worker.resume.compose_integration.resume_step1

import android.net.Uri
import com.capstone.nongglenonggle.core.base.UiEffect
import com.capstone.nongglenonggle.core.base.UiEvent
import com.capstone.nongglenonggle.core.base.UiState
import java.util.Date

class ResumeStep1Contract {
    data class State(
        val isLoading: Boolean = false,
        val userName: String = "",
        val selectedGender: String = "",
        val haveCertification: Boolean? = null,
        val imageProfileUri: Uri? = null,
        val birthDate: Date? = null,
        val birthDatePresnet: String = "생년월일을 선택해주세요.",
        val userCertificateType: String = "",
        val userCertificationList: MutableList<String> = mutableListOf(),
    ) : UiState

    sealed interface Event : UiEvent {
        data class SetGenderType(val gender: String) : Event
        data class SetCertificateAvailable(val value: Boolean) : Event
        object ClearUserName : Event
        data class SetUserName(val name: String) : Event
        data class SetBirthDate(val birthDate: Date) : Event
        data class SetUserCertificateDetail(val certificate: String) : Event
        object ClearUserCertificateDetail : Event
        data class AddCertificationChip(val certificationTitle: String) : Event
    }

    sealed interface Effect : UiEffect {
        data class UnAvailableToastmessage(val message: String): Effect
        object OpenGallery : Effect
    }
}