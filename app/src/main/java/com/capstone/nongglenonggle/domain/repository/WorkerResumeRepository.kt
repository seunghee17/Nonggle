package com.capstone.nongglenonggle.domain.repository

import android.net.Uri
import com.capstone.nongglenonggle.data.network.AppResult

interface WorkerResumeRepository {
    suspend fun setWorkerProfileImage(imageUri: Uri): AppResult<String>
}