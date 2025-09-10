package com.capstone.nongglenonggle.domain.usecase

import android.net.Uri
import com.capstone.nongglenonggle.data.network.AppResult
import com.capstone.nongglenonggle.domain.qualifiers.IoDispatcher
import com.capstone.nongglenonggle.domain.repository.WorkerResumeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SetWorkderProfileImageUseCase @Inject constructor(
    private val workerResumeRepository: WorkerResumeRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(imageUri: Uri) : AppResult<String> {
        return withContext(ioDispatcher) { workerResumeRepository.setWorkerProfileImage(imageUri) }
    }
}