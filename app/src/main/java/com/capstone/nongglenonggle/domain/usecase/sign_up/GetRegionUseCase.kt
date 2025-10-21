package com.capstone.nongglenonggle.domain.usecase.sign_up

import com.capstone.nongglenonggle.data.model.remote_model.RegionModel
import com.capstone.nongglenonggle.data.network.ApiResult
import com.capstone.nongglenonggle.domain.qualifiers.IoDispatcher
import com.capstone.nongglenonggle.domain.repository.RemoteDataRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

//서버 로부터 전체 지역 데이터 받아옴
class GetRegionUseCase @Inject constructor(
    private val remoteDataRepository: RemoteDataRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): ApiResult<RegionModel> =
        withContext(ioDispatcher) { remoteDataRepository.getRegion() }
}