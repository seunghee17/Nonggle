package com.capstone.nongglenonggle.data.repositoryimpl

import com.capstone.nongglenonggle.data.local_datasource.RegionDao
import com.capstone.nongglenonggle.data.model.remote_model.SubRegion
import com.capstone.nongglenonggle.domain.qualifiers.IoDispatcher
import com.capstone.nongglenonggle.domain.repository.LocalDataRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Singleton
import javax.inject.Inject

@Singleton
class LocalDataRepositoryImpl @Inject constructor(
    private val regionDao: RegionDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): LocalDataRepository {

    override suspend fun saveRegionToLocalDB(subRegions: List<SubRegion>) {
        return withContext(ioDispatcher) {
            regionDao.insertRegionsWithDistricts(subRegions)
        }
    }
}