package com.capstone.nongglenonggle.presentation.view.worker.resume.main_screen

import android.net.Uri
import com.capstone.nongglenonggle.core.base.UiEffect
import com.capstone.nongglenonggle.core.base.UiEvent
import com.capstone.nongglenonggle.core.base.UiState
import com.capstone.nongglenonggle.data.model.worker.UserCareerListItemModel

class ResumeTabContract {
    data class State(
      val isLoading: Boolean = false
    ): UiState

    sealed interface Event: UiEvent {
        data class SaveResume(
            val name: String,
            val birthDate: String,
            val gender: String,
            val certificate: Boolean,
            val certificateList: List<String>,
            val careerList: List<UserCareerListItemModel>,
            val regionList: List<String>,
            val categoryList: List<String>,
            val imageUri: Uri?
        ): Event
    }

    sealed interface Effect : UiEffect {
        object NavigateToHomeScreen: Effect
        data class ShowToastMessage(val message: String): Effect
    }
}