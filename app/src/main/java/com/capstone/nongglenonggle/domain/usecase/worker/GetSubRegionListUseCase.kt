package com.capstone.nongglenonggle.domain.usecase.worker

import com.capstone.nongglenonggle.domain.qualifiers.IoDispatcher
import com.capstone.nongglenonggle.domain.repository.RegionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSubRegionListUseCase @Inject constructor(
    private val regionRepository: RegionRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(parentRegion: String): List<String> = withContext(ioDispatcher) {
        regionRepository.getSubRegionList(parentRegion)
    }
}