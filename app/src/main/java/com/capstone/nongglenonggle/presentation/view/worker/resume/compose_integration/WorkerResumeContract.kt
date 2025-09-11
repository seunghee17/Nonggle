package com.capstone.nongglenonggle.presentation.view.worker.resume.compose_integration

import com.capstone.nongglenonggle.core.base.UiEffect
import com.capstone.nongglenonggle.core.base.UiEvent
import com.capstone.nongglenonggle.core.base.UiState
import com.capstone.nongglenonggle.data.model.worker.ResumeStep1State
import com.capstone.nongglenonggle.data.model.worker.ResumeStep2State
import com.capstone.nongglenonggle.data.model.worker.ResumeStep3State
import com.capstone.nongglenonggle.data.model.worker.ResumeStep4State
import com.capstone.nongglenonggle.presentation.view.login.LoginContract.Effect
import java.util.Date

class WorkerResumeContract {
    data class State(
        val step2: ResumeStep2State = ResumeStep2State(),
        val step3: ResumeStep3State = ResumeStep3State(),
        val step4: ResumeStep4State = ResumeStep4State(),
    ) : UiState

    sealed interface Event : UiEvent {
        sealed interface Step1 : Event {
            data class SetGenderType(val gender: String) : Step1
            data class SetCertificateAvailable(val value: Boolean) : Step1
            object ClearUserName : Step1
            data class SetUserName(val name: String) : Step1
            data class SetBirthDate(val birthDate: Date) : Step1
            data class SetUserCertificateDetail(val certificate: String) : Step1
            object ClearUserCertificateDetail : Step1
            data class AddCertificationChip(val certificationTitle: String) : Step1
        }

        sealed interface Step2 : Event {
            data class SetCareerTitle(val title: String) : Step2
            object ClearCareerTitle : Step2
            data class SetWorkPeriodRange(val isMonthOver: Boolean): Step2
            data class SetCareerDetail(val detail: String): Step2
        }

        sealed interface Step3 : Event {
            data class SetIntroduceDetail(val detail: String) :Step3
            object ClearIntroduceDetail: Step3
            data class SetPersonalityType(val type: String): Step3
            object ClearPersonalityType
            data class SetAdditionalDetailComment(val comment: String): Step3
        }
        sealed interface Step4 : Event {
            data class AddWorkCategoryChip(val value: String) : Step4
            data class RemoveWorkCategoryChip(val value: String) : Step4
        }
    }

    sealed interface Effect : UiEffect {
        data class UnAvailableToastmessage(val message: String): Effect
        sealed interface Step1 : Effect {
            object OpenGallery : Step1
        }
        sealed interface Step2: Effect {

        }
    }
}