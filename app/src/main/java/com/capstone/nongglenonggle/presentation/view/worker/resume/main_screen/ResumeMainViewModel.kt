package com.capstone.nongglenonggle.presentation.view.worker.resume.main_screen

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.capstone.nongglenonggle.core.base.BaseViewModel
import com.capstone.nongglenonggle.data.AppResult
import com.capstone.nongglenonggle.data.model.worker.UserCareerListItemModel
import com.capstone.nongglenonggle.data.model.worker.UserResumeModel
import com.capstone.nongglenonggle.domain.usecase.worker.SetWorkerProfileImageUseCase
import com.capstone.nongglenonggle.domain.usecase.worker.SetWorkerResumeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.capstone.nongglenonggle.presentation.view.worker.resume.main_screen.ResumeTabContract.Event as MainEvent
import com.capstone.nongglenonggle.presentation.view.worker.resume.main_screen.ResumeTabContract.State as MainState
import com.capstone.nongglenonggle.presentation.view.worker.resume.main_screen.ResumeTabContract.Effect as MainEffect

@HiltViewModel
class ResumeMainViewModel @Inject constructor(
    private val setWorkerResumeUseCase: SetWorkerResumeUseCase,
    private val setWorkerProfileImageUseCase: SetWorkerProfileImageUseCase
) : BaseViewModel<MainEvent, MainState, MainEffect>(
    initialState = ResumeTabContract.State()
){
    override fun handleEvent(event: MainEvent) {
        super.handleEvent(event)
        when(event) {
            is ResumeTabContract.Event.SaveResume -> {
                postEffect(effect = ResumeTabContract.Effect.ShowToastMessage("이력서 저장 중입니다."))
                val isSubmitAvailable = submitValidate(
                    name = event.name,
                    birthDate = event.birthDate,
                    gender = event.gender,
                    certificate = event.certificate,
                    certificateList = event.certificateList,
                    regionList = event.regionList,
                    categoryList = event.categoryList
                )
                if(!isSubmitAvailable) {
                    postEffect(effect = ResumeTabContract.Effect.ShowToastMessage("미입력 정보를 마저 입력해주세요."))
                    return
                } else {
                    viewModelScope.launch {
                        saveResume(
                            name = event.name,
                            birthDate = event.birthDate,
                            gender = event.gender,
                            certificate = event.certificate,
                            certificateList = event.certificateList,
                            regionList = event.regionList,
                            categoryList = event.categoryList,
                            careerList = event.careerList,
                            imageUri = event.imageUri
                        )
                        postEffect(effect = MainEffect.ShowToastMessage("저장을 완료했습니다."))
                        postEffect(effect = MainEffect.NavigateToHomeScreen)
                    }
                }
            }
        }
    }

    private fun submitValidate(
        name: String,
        birthDate: String,
        gender: String,
        certificate: Boolean,
        certificateList: List<String>,
        regionList: List<String>,
        categoryList: List<String>,
    ): Boolean {
        if (name.isEmpty()) return false
        if (birthDate.isEmpty()) return false
        if (gender.isEmpty()) return false

        // 자격증 정보 검사
        if (certificate && certificateList.isEmpty()) return false

        // 지역 및 품목 검사
        if (regionList.isEmpty()) return false
        if (categoryList.isEmpty()) return false

        // 모든 항목이 조건 충족 시
        return true
    }

    private suspend fun saveResume(
        name: String,
        birthDate: String,
        gender: String,
        certificate: Boolean,
        certificateList: List<String>,
        careerList: List<UserCareerListItemModel>,
        regionList: List<String>,
        categoryList: List<String>,
        imageUri: Uri?,
    ) {
        try {
            saveUserImage(imageUri)
            setWorkerResumeUseCase.invoke(
                UserResumeModel(
                    name = name,
                    birthDate = birthDate,
                    gender = gender,
                    certificate = certificate,
                    certificateList = certificateList,
                    careerList = careerList,
                    regionList = regionList,
                    categoryList = categoryList,
                )
            )
        } catch (e: Exception) {

        }
    }

    private suspend fun saveUserImage(imageUri: Uri?) {
        if(imageUri == null) return
        val result = setWorkerProfileImageUseCase.invoke(imageUri)
        when(result) {
            is AppResult.Failure -> {
                postEffect(effect = MainEffect.ShowToastMessage("이미지 저장에 실패하여 기본 이미지로 대체됩니다."))
            }
            else -> {}
        }
    }
}