package com.capstone.nongglenonggle.domain.usecase

import com.capstone.nongglenonggle.data.model.worker.RegionListModel
import com.capstone.nongglenonggle.domain.qualifiers.IoDispatcher
import com.capstone.nongglenonggle.domain.repository.RegionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveRegionToLocalDataBaseUseCase @Inject constructor(
    private val regionRepository: RegionRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(subRegions: List<RegionListModel>) = withContext(ioDispatcher) {
        regionRepository.saveRegionToLocalDB(subRegions)
    }
}