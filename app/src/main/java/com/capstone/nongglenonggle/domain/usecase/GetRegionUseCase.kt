package com.capstone.nongglenonggle.domain.usecase

import com.capstone.nongglenonggle.data.model.remote_model.RegionModel
import com.capstone.nongglenonggle.data.remote_datasource.ApiResult
import com.capstone.nongglenonggle.domain.qualifiers.IoDispatcher
import com.capstone.nongglenonggle.domain.repository.RemoteDataRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetRegionUseCase @Inject constructor(
    private val remoteDataRepository: RemoteDataRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): ApiResult<RegionModel> =
        withContext(ioDispatcher) { remoteDataRepository.getRegion() }
}