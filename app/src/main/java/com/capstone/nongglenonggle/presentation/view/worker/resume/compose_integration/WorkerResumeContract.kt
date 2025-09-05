package com.capstone.nongglenonggle.presentation.view.worker.resume.compose_integration

import com.capstone.nongglenonggle.core.base.UiEffect
import com.capstone.nongglenonggle.core.base.UiEvent
import com.capstone.nongglenonggle.core.base.UiState
import com.capstone.nongglenonggle.data.model.worker.ResumeStep1State
import com.capstone.nongglenonggle.data.model.worker.ResumeStep2State
import java.util.Date

class WorkerResumeContract {
    data class State(
        val step1: ResumeStep1State = ResumeStep1State(),
        val step2: ResumeStep2State = ResumeStep2State(),
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
        }

        sealed interface Step3 : Event { /* ... */ }
        sealed interface Step4 : Event {/* ... */}
    }

    sealed interface Effect : UiEffect {
        sealed interface Step1 : Effect {
            object OpenGallery : Step1
        }
        sealed interface Step2: Effect {

        }
    }
}