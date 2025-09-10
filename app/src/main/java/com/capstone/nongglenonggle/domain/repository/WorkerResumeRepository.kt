package com.capstone.nongglenonggle.domain.repository

import android.net.Uri
import com.capstone.nongglenonggle.data.network.AppResult

interface WorkerResumeRepository {
    //이미지 firestore 업로드 동작
    suspend fun setWorkerProfileImage(imageUri: Uri): AppResult<String>
}