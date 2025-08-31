package com.capstone.nongglenonggle.presentation.view.worker.resume

import android.net.Uri
import com.capstone.nongglenonggle.core.base.UiEffect
import com.capstone.nongglenonggle.core.base.UiEvent
import com.capstone.nongglenonggle.core.base.UiState
import com.capstone.nongglenonggle.data.model.worker.ResumeDataClass
import com.capstone.nongglenonggle.domain.entity.ResumeSummary
import com.capstone.nongglenonggle.presentation.view.signup.SignupContract
import com.capstone.nongglenonggle.presentation.view.signup.SignupContract.Event
import java.util.Date

class WorkerResumeContract {
    data class State(
        val isLoading: Boolean = true,
        val addressTextfieldData: String = "",
        val selectedGender: String = "",
        val haveCertification: Boolean? = null,
        val imageProfileUri: Uri? = null,
        val birthDate: Date? = null,
        val birthDatePresnet: String = "생년월일을 선택해주세요."
    ): UiState

    sealed class Event: UiEvent {
        data class SetGenderType(val gender: String): Event()
        data class ChangeCertificateState(val value: Boolean): Event()
        object ClearName: Event()
        data class InputName(val name: String): Event()
        data class SetBirthDate(val birthDate: Date): Event()
    }

    sealed class Effect: UiEffect {
        object OpenGallery: Effect()
        object OpenBirthBottomSheet: Effect()
    }
}