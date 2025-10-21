package com.capstone.nongglenonggle.data.repositoryimpl

import com.capstone.nongglenonggle.data.model.remote_model.RegionModel
import com.capstone.nongglenonggle.data.network.ApiResult
import com.capstone.nongglenonggle.data.network.ApiService
import com.capstone.nongglenonggle.domain.qualifiers.IoDispatcher
import com.capstone.nongglenonggle.domain.repository.RemoteDataRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataRepositoryImpl @Inject constructor(
    private val service: ApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): RemoteDataRepository {
    override suspend fun getRegion(): ApiResult<RegionModel> {
        return withContext(ioDispatcher) {
            service.getRegions()
        }
    }
}