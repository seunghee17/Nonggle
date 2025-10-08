package com.capstone.nongglenonggle.domain.repository

import android.net.Uri
import com.capstone.nongglenonggle.data.AppResult
import com.capstone.nongglenonggle.data.model.worker.UserResumeModel

interface WorkerResumeRepository {
    //이미지 firestore 업로드 동작
    suspend fun setWorkerProfileImage(imageUri: Uri): AppResult<String>
    //이력서 정보 저장
    suspend fun setWorkerResume(resume: UserResumeModel): AppResult<Unit>
}