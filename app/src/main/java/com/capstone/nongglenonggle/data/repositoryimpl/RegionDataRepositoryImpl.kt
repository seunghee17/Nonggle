package com.capstone.nongglenonggle.data.repositoryimpl

import com.capstone.nongglenonggle.data.local_datasource.RegionDao
import com.capstone.nongglenonggle.data.model.worker.RegionListModel
import com.capstone.nongglenonggle.domain.qualifiers.IoDispatcher
import com.capstone.nongglenonggle.domain.repository.RegionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Singleton
import javax.inject.Inject

@Singleton
class RegionDataRepositoryImpl @Inject constructor(
    private val regionDao: RegionDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): RegionRepository {

    override suspend fun saveRegionToLocalDB(subRegions: List<RegionListModel>) {
        return withContext(ioDispatcher) {
            regionDao.insertRegionsWithDistricts(subRegions)
        }
    }

    override suspend fun getRegionList(): List<String> {
        return withContext(ioDispatcher) {
            regionDao.getAllRegion()
        }
    }

    override suspend fun getSubRegionList(parentRegion: String): List<String> {
        return withContext(ioDispatcher) {
            regionDao.getDistrictsByRegion(parentRegion).map { it.name }
        }
    }


}