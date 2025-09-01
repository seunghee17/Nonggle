package com.capstone.nongglenonggle.domain.repository

import android.net.Uri

interface WorkerResumeRepository {
    suspend fun setWorkerProfileImage(imageUri: Uri): Result<String>
}