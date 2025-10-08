package com.capstone.nongglenonggle.domain.usecase.worker

import com.capstone.nongglenonggle.data.AppResult
import com.capstone.nongglenonggle.data.model.worker.UserResumeModel
import com.capstone.nongglenonggle.domain.qualifiers.IoDispatcher
import com.capstone.nongglenonggle.domain.repository.WorkerResumeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SetWorkerResumeUseCase @Inject constructor(
    private val workerResumeRepository: WorkerResumeRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(resume: UserResumeModel): AppResult<Unit> {
        return withContext(ioDispatcher) { workerResumeRepository.setWorkerResume(resume) }
    }
}