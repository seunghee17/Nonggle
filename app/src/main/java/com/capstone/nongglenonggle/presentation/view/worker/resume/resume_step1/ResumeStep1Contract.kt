package com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step1

import android.net.Uri
import com.capstone.nongglenonggle.core.base.UiEffect
import com.capstone.nongglenonggle.core.base.UiEvent
import com.capstone.nongglenonggle.core.base.UiState
import java.time.LocalDate
import java.util.Date
import java.util.LinkedHashMap

class ResumeStep1Contract {
    data class State(
        val isLoading: Boolean = false,
        val userName: String = "",
        val showDatePickerSheet: Boolean = false,
        val genderSelectedMap: LinkedHashMap<String, Boolean> = linkedMapOf(
            "여" to false,
            "남" to false
        ),
        val certificationPossessionSelectedMap: LinkedHashMap<String, Boolean> = linkedMapOf(
            "있음" to false,
            "없음" to false
        ),
        val imageProfileUri: Uri? = null,
        val birthDate: LocalDate? = null,
        val birthDatePresent: String = "생년월일을 선택해주세요.",
        val userCertificateType: String = "",
        val userCertificationList: List<String> = emptyList(),
    ) : UiState

    sealed interface Event : UiEvent {
        data class UpdateDatePickerSheet(val sheetState: Boolean) : Event
        data class SetGenderType(val gender: String) : Event
        data class GetImageFromGallery(val uri: Uri) : Event
        data class OpenGallery(
            val isPhotoPickerAvailable: Boolean,
            val getContentLauncher: () -> Unit,
            val pickerLauncher: () -> Unit
        ) : Event

        object ClearUserName : Event
        data class SetUserName(val name: String) : Event
        data class SetBirthDate(val birthDate: Date) : Event
        data class SetCertificateAvailable(val optionKey: String) : Event

        //자격증 textfield 입력 및 지우기
        data class SetUserCertificateDetail(val certificate: String) : Event
        object ClearUserCertificateDetail : Event

        //자격증 chip 추가
        data class AddCertificationChip(val certificationTitle: String) : Event
        data class RemoveCertificationChip(val certificationTitle: String) : Event
    }

    sealed interface Effect : UiEffect {
        data class UnAvailableToastmessage(val message: String) : Effect
    }
}